package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.KeyCode;

public class PauseMenu {

    private final Stage pauseStage;
    private final Runnable onResume;
    private final Runnable onExit;
    private final Runnable onSettings;

    public PauseMenu(Stage primaryStage, Runnable onResume, Runnable onExit, Runnable onSettings) {
        this.onResume = onResume;
        this.onExit = onExit;
        this.onSettings = onSettings;

        // Create the pause stage
        pauseStage = new Stage();
        pauseStage.initOwner(primaryStage);
        pauseStage.initModality(Modality.APPLICATION_MODAL);
        pauseStage.initStyle(StageStyle.UNDECORATED);
        pauseStage.setResizable(false);

        // Background
        StackPane root = new StackPane();
        Rectangle background = new Rectangle(400, 600);
        background.setFill(Color.rgb(30, 30, 30, 0.9)); // Grayish-black background
        root.getChildren().add(background);

        // Title Text
        Text title = new Text("Game Paused");
        title.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/Roboto-Regular.ttf"), 40));
        title.setFill(Color.CYAN); // Neon blue text

        // Buttons
        Button playButton = createStyledButton("Continue");
        playButton.setOnAction(event -> resumeGame());

        Button settingsButton = createStyledButton("Settings");
        settingsButton.setOnAction(event -> openSettings());

        Button exitButton = createStyledButton("Exit");
        exitButton.setOnAction(event -> exitGame());

        // Layout
        VBox menu = new VBox(20, title, playButton, settingsButton, exitButton);
        menu.setAlignment(Pos.CENTER);
        root.getChildren().add(menu);

        Scene pauseScene = new Scene(root, 400, 600);
        pauseStage.setScene(pauseScene);

        // Add key press handler to resume on 'P'
        pauseScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P) {
                resumeGame();
            }
        });
    }

    public void show() {
        pauseStage.show();
    }

    public void hide() {
        pauseStage.close();
    }

    private void resumeGame() {
        hide();
        if (onResume != null) {
            onResume.run();
        }
    }

    private void exitGame() {
        hide();
        if (onExit != null) {
            onExit.run();
        }
    }

    private void openSettings() {
        hide();
        if (onSettings != null) {
            onSettings.run();
        }
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/Roboto-Regular.ttf"), 20);
        if (customFont == null) {
            customFont = Font.font("Arial", 20); // Fallback font
        }
        button.setFont(customFont);
        button.setTextFill(Color.CYAN); // Neon blue text
        button.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: cyan; " +
                        "-fx-border-width: 2; " +
                        "-fx-padding: 10; " +
                        "-fx-background-radius: 5; " +
                        "-fx-border-radius: 5;"
        );

        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: cyan; " +
                        "-fx-border-color: cyan; " +
                        "-fx-border-width: 2; " +
                        "-fx-padding: 10; " +
                        "-fx-background-radius: 5; " +
                        "-fx-border-radius: 5;" +
                        "-fx-text-fill: black;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: cyan; " +
                        "-fx-border-width: 2; " +
                        "-fx-padding: 10; " +
                        "-fx-background-radius: 5; " +
                        "-fx-border-radius: 5;"
        ));

        button.setPrefWidth(200); // Ensure all buttons are the same width
        return button;
    }
}
