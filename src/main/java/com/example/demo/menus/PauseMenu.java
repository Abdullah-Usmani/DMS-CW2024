package com.example.demo.menus;

import com.example.demo.managers.StyleManager;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PauseMenu extends Parent {

    private final Stage pauseStage;
    private final Runnable onResume;
    private final Runnable onRestartLevel;
    private final Runnable onRestartGame;
    private final Runnable onExitToMainMenu;

    public PauseMenu(Stage primaryStage, Runnable onResume, Runnable onRestartLevel, Runnable onRestartGame, Runnable onExitToMainMenu) {
        this.onResume = onResume;
        this.onRestartLevel = onRestartLevel;
        this.onRestartGame = onRestartGame;
        this.onExitToMainMenu = onExitToMainMenu;

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
        Label title = StyleManager.createStyledLabel("Game Paused", true, 0.05);

        // Buttons
        Button playButton = StyleManager.createStyledButton("Continue");
        playButton.setOnAction(event -> resumeGame());

        Button restartLevelButton = StyleManager.createStyledButton("Restart Level");
        restartLevelButton.setOnAction(event -> restartLevel());

        Button restartGameButton = StyleManager.createStyledButton("Restart Game");
        restartGameButton.setOnAction(event -> restartGame());

        Button exitToMainMenuButton = StyleManager.createStyledButton("Exit to Main Menu");
        exitToMainMenuButton.setOnAction(event -> exitToMainMenu());

        // Layout
        VBox menu = new VBox(20, title, playButton, restartLevelButton, restartGameButton, exitToMainMenuButton);
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

    private void restartLevel() {
        hide();
        if (onRestartLevel != null) {
            onRestartLevel.run();
        }
    }

    private void restartGame() {
        hide();
        if (onRestartGame != null) {
            onRestartGame.run();
        }
    }

    private void exitToMainMenu() {
        hide();
        if (onExitToMainMenu != null) {
            onExitToMainMenu.run();
        }
    }
}
