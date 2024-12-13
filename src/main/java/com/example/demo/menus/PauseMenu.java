/**
 * Represents the pause menu for the game "F-15: Strike Eagle."
 *
 * The PauseMenu class displays a modal menu when the game is paused, allowing
 * the user to resume the game, restart the current level, restart the game, or exit to the main menu.
 */
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * PauseMenu handles the user interface for pausing the game and provides
 * options to resume, restart, or exit the game.
 */
public class PauseMenu extends Parent {

    /** The stage used for displaying the pause menu. */
    private final Stage pauseStage;

    /** Callback for resuming the game. */
    private final Runnable onResume;

    /** Callback for restarting the current level. */
    private final Runnable onRestartLevel;

    /** Callback for restarting the game. */
    private final Runnable onRestartGame;

    /** Callback for exiting to the main menu. */
    private final Runnable onExitToMainMenu;

    /**
     * Constructs a PauseMenu with specified callbacks for user actions.
     *
     * @param primaryStage       the primary stage of the application.
     * @param onResume           the callback to execute when resuming the game.
     * @param onRestartLevel     the callback to execute when restarting the current level.
     * @param onRestartGame      the callback to execute when restarting the game.
     * @param onExitToMainMenu   the callback to execute when exiting to the main menu.
     */
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

        // Create the background
        StackPane root = new StackPane();
        Rectangle background = new Rectangle(400, 600);
        background.setFill(Color.rgb(30, 30, 30, 0.9)); // Grayish-black background
        root.getChildren().add(background);

        // Create the title label
        Label title = StyleManager.createStyledLabel("Game Paused", true, 0.05);

        // Create buttons with associated actions
        Button playButton = StyleManager.createStyledButton("Continue");
        playButton.setOnAction(event -> resumeGame());

        Button restartLevelButton = StyleManager.createStyledButton("Restart Level");
        restartLevelButton.setOnAction(event -> restartLevel());

        Button restartGameButton = StyleManager.createStyledButton("Restart Game");
        restartGameButton.setOnAction(event -> restartGame());

        Button exitToMainMenuButton = StyleManager.createStyledButton("Exit to Main Menu");
        exitToMainMenuButton.setOnAction(event -> exitToMainMenu());

        // Create the layout
        VBox menu = new VBox(20, title, playButton, restartLevelButton, restartGameButton, exitToMainMenuButton);
        menu.setAlignment(Pos.CENTER);
        root.getChildren().add(menu);

        // Set up the scene
        Scene pauseScene = new Scene(root, 400, 600);
        pauseStage.setScene(pauseScene);

        // Add key press handler to resume on 'P'
        pauseScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P) {
                resumeGame();
            }
        });
    }

    /**
     * Displays the pause menu.
     */
    public void show() {
        pauseStage.show();
    }

    /**
     * Hides the pause menu.
     */
    public void hide() {
        pauseStage.close();
    }

    /**
     * Resumes the game and executes the onResume callback.
     */
    private void resumeGame() {
        hide();
        if (onResume != null) {
            onResume.run();
        }
    }

    /**
     * Restarts the current level and executes the onRestartLevel callback.
     */
    private void restartLevel() {
        hide();
        if (onRestartLevel != null) {
            onRestartLevel.run();
        }
    }

    /**
     * Restarts the game and executes the onRestartGame callback.
     */
    private void restartGame() {
        hide();
        if (onRestartGame != null) {
            onRestartGame.run();
        }
    }

    /**
     * Exits to the main menu and executes the onExitToMainMenu callback.
     */
    private void exitToMainMenu() {
        hide();
        if (onExitToMainMenu != null) {
            onExitToMainMenu.run();
        }
    }
}
