package com.example.demo.displays;

import com.example.demo.Config;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class StartOverlay {
    private final Group root;
    private final Runnable startGame;

    public StartOverlay(Group root, Runnable startGame) {
        this.root = root;
        this.startGame = startGame;
    }

    public void showLevelOverlay(String levelName, List<ActorInfo> actorsInfo, int killsNeeded) {
        // Create the overlay container
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: black;"); // Black background
        overlay.setPrefSize(root.getScene().getWidth(), root.getScene().getHeight());

        // Main overlay label for the level info
        Label overlayText = new Label(
                "Level: " + levelName + "\n" +
                        "Kills Needed: " + killsNeeded
        );
        overlayText.setStyle("-fx-font-family: 'Arial'; " +
                "-fx-font-size: 36px; " +
                "-fx-text-fill: #00ffff; " + // Neon blue color
                "-fx-effect: dropshadow(gaussian, #00ffff, 15, 0.5, 0, 0);");

        // Create separate lists for each category
        List<ActorInfo> userPlanes = new ArrayList<>();
        List<ActorInfo> userProjectiles = new ArrayList<>();
        List<ActorInfo> enemyPlanes = new ArrayList<>();
        List<ActorInfo> enemyProjectiles = new ArrayList<>();

        // Categorize actors
        for (ActorInfo info : actorsInfo) {
            if (info.isPlane) {
                if (info.isFriendly) {
                    userPlanes.add(info);
                } else {
                    enemyPlanes.add(info);
                }
            } else {
                if (info.isFriendly) {
                    userProjectiles.add(info);
                } else {
                    enemyProjectiles.add(info);
                }
            }
        }
        // Create boxes for each category
        VBox userPlanesBox = createCategoryBox("User Planes", userPlanes);
        VBox userProjectilesBox = createCategoryBox("User Projectiles", userProjectiles);
        VBox enemyPlanesBox = createCategoryBox("Enemy Planes", enemyPlanes);
        VBox enemyProjectilesBox = createCategoryBox("Enemy Projectiles", enemyProjectiles);

        // Layout the categories in an HBox
        HBox categoriesBox = new HBox(20, userPlanesBox, userProjectilesBox, enemyPlanesBox, enemyProjectilesBox);
        categoriesBox.setAlignment(Pos.TOP_CENTER);

        // Combine everything into the overlay
        VBox overlayContent = new VBox(20, overlayText, categoriesBox); // Spacing of 20
        overlayContent.setAlignment(Pos.CENTER);

        overlay.getChildren().add(overlayContent);
        root.getChildren().add(overlay);

        // Fade-out transition for the overlay
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), overlay);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Remove overlay and start the game after fade-out
        fadeOut.setOnFinished(e -> {
            root.getChildren().remove(overlay);
            startGame.run(); // Start the game
        });

        // Pause before fade-out
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> fadeOut.play());

        delay.play(); // Begin the delay
    }

    // Create a function to generate a VBox for each category
    public VBox createCategoryBox(String title, List<ActorInfo> actors) {
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-text-fill: white;");
        VBox categoryBox = new VBox(10, titleLabel); // Spacing of 10
        categoryBox.setAlignment(Pos.TOP_CENTER);

        for (ActorInfo info : actors) {
            HBox actorRow = new HBox(20);
            actorRow.setAlignment(Pos.CENTER_LEFT);

            ImageView actorImage = new ImageView(getClass().getResource(info.imagePath).toExternalForm());
            actorImage.setFitWidth(Config.getScreenWidth() * 0.08);
            actorImage.setFitHeight(Config.getScreenHeight() * 0.04);

            Label actorName = new Label(info.name);
            actorName.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: white;");

            String typeText = info.isPlane ? "Health: " : "Damage: ";
            Label actorStat = new Label(typeText + info.statValue);
            actorStat.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: white;");

            actorRow.getChildren().addAll(actorImage, actorName, actorStat);
            categoryBox.getChildren().add(actorRow);
        }
        return categoryBox;
    }
}
