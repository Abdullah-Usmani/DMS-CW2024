/**
 * Represents the start overlay for a game level in "F-15: Strike Eagle."
 *
 * The StartOverlay class displays introductory information about the current level,
 * including required kills and actors involved, before the game begins.
 */
package com.example.demo.displays;

import com.example.demo.Config;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * StartOverlay manages the level introduction, displaying details about the actors,
 * required kills, and other relevant information.
 */
public class StartOverlay {

    /** The root group to which the overlay is added. */
    private final Group root;

    /** The runnable action to start the game after the overlay is dismissed. */
    private final Runnable startGame;

    /**
     * Constructs a StartOverlay instance.
     *
     * @param root      the root group for adding the overlay.
     * @param startGame the runnable action to start the game.
     */
    public StartOverlay(Group root, Runnable startGame) {
        this.root = root;
        this.startGame = startGame;
    }

    /**
     * Displays the level overlay with information about the current level.
     *
     * @param levelName   the name of the level.
     * @param actorsInfo  a list of ActorInfo objects describing the actors in the level.
     * @param killsNeeded the number of kills required to complete the level.
     */
    public void showLevelOverlay(String levelName, List<ActorInfo> actorsInfo, int killsNeeded) {
        // Create the overlay container
        StackPane overlay = new StackPane();
        overlay.setPrefSize(root.getScene().getWidth(), root.getScene().getHeight());

        // Semi-transparent background
        Rectangle semiTransparentOverlay = new Rectangle(Config.getScreenWidth(), Config.getScreenHeight());
        semiTransparentOverlay.setFill(Color.rgb(0, 0, 0, 0.6)); // Black with 60% opacity

        // Main overlay label for level details
        Label overlayText = new Label(
                "Level: " + levelName + "\n" +
                        "Kills Needed: " + killsNeeded
        );
        overlayText.setStyle("-fx-font-family: 'HornetDisplay-Bold'; " +
                "-fx-font-size: " + (Config.getScreenHeight() * 0.05) + "px; " +
                "-fx-text-fill: #00FF00; " + // Neon green color
                "-fx-effect: dropshadow(gaussian, #00FF00, 15, 0.5, 0, 0);");

        // Categorize actors into groups
        List<ActorInfo> userPlanes = new ArrayList<>();
        List<ActorInfo> userProjectiles = new ArrayList<>();
        List<ActorInfo> enemyPlanes = new ArrayList<>();
        List<ActorInfo> enemyProjectiles = new ArrayList<>();

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

        // Layout the categories horizontally
        HBox categoriesBox = new HBox(Config.getScreenWidth() * 0.02, userPlanesBox, userProjectilesBox, enemyPlanesBox, enemyProjectilesBox);
        categoriesBox.setAlignment(Pos.TOP_CENTER);

        // Combine overlay elements
        VBox overlayContent = new VBox(Config.getScreenHeight() * 0.03, overlayText, categoriesBox);
        overlayContent.setAlignment(Pos.CENTER);

        // Add elements to the overlay
        overlay.getChildren().addAll(semiTransparentOverlay, overlayContent);
        root.getChildren().add(overlay);

        // Transition effects for the overlay
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), overlay);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Remove overlay and start game after fade-out
        fadeOut.setOnFinished(e -> {
            root.getChildren().remove(overlay);
            startGame.run();
        });

        // Pause before fade-out
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> fadeOut.play());
        delay.play();
    }

    /**
     * Creates a category box to display actor details.
     *
     * @param title  the title of the category.
     * @param actors the list of ActorInfo objects to display.
     * @return a VBox containing the actor details.
     */
    public VBox createCategoryBox(String title, List<ActorInfo> actors) {
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-family: 'HornetDisplay-Regular'; " +
                "-fx-font-size: " + (Config.getScreenHeight() * 0.03) + "px; " +
                "-fx-text-fill: #FFFF00;"); // Yellow color

        VBox categoryBox = new VBox(Config.getScreenHeight() * 0.02, titleLabel); // Dynamic spacing
        categoryBox.setAlignment(Pos.TOP_CENTER);

        for (ActorInfo info : actors) {
            HBox actorRow = new HBox(Config.getScreenWidth() * 0.01);
            actorRow.setAlignment(Pos.CENTER_LEFT);

            ImageView actorImage = new ImageView(getClass().getResource(info.imagePath).toExternalForm());
            actorImage.setFitWidth(Config.getScreenWidth() * 0.08);
            actorImage.setFitHeight(Config.getScreenHeight() * 0.04);

            Label actorName = new Label(info.name);
            actorName.setStyle("-fx-font-family: 'HornetDisplay-Regular'; -fx-font-size: " +
                    (Config.getScreenHeight() * 0.025) + "px; -fx-text-fill: white;");

            ImageView statIcon = new ImageView(
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(info.isPlane ? Config.HEART_IMAGE : Config.KILL_IMAGE)))
            );
            statIcon.setFitWidth(Config.getScreenWidth() * 0.02);
            statIcon.setFitHeight(Config.getScreenWidth() * 0.02);

            Label actorStat = new Label("" + info.statValue);
            actorStat.setStyle("-fx-font-family: 'HornetDisplay-Regular'; -fx-font-size: " +
                    (Config.getScreenHeight() * 0.025) + "px; -fx-text-fill: white;");

            actorRow.getChildren().addAll(actorImage, actorName, statIcon, actorStat);
            categoryBox.getChildren().add(actorRow);
        }
        return categoryBox;
    }
}
