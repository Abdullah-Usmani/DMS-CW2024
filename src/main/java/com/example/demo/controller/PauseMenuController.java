/**
 * Controller for managing the pause menu in "F-15: Strike Eagle."
 *
 * The PauseMenuController class handles user interactions with the pause menu,
 * including pausing and resuming the game, restarting the level or game, and returning
 * to the main menu.
 */
package com.example.demo.controller;

import com.example.demo.levels.LevelParent;
import com.example.demo.menus.PauseMenu;
import javafx.stage.Stage;
import java.util.logging.Logger;

/**
 * PauseMenuController manages the logic for toggling the pause menu and handling user actions.
 */
public class PauseMenuController {

    /** The pause menu instance. */
    private final PauseMenu pauseMenu;

    /** The currently active level. */
    private LevelParent currentLevel;

    /** The main game controller. */
    private final Controller controller;

    /** Indicates whether the game is currently paused. */
    private boolean isPaused;

    /** Logger for debugging and error tracking. */
    Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Constructs a PauseMenuController with the specified stage and game controller.
     *
     * @param stage      the primary stage of the application.
     * @param controller the main game controller.
     */
    public PauseMenuController(Stage stage, Controller controller) {
        this.isPaused = false;
        this.controller = controller;
        this.pauseMenu = new PauseMenu(stage, this::resumeGame, this::restartCurrentLevel, this::restartGame, this::goToMainMenu);
    }

    /**
     * Sets the currently active level.
     *
     * @param level the current level to set.
     */
    public void setCurrentLevel(LevelParent level) {
        this.currentLevel = level;
    }

    /**
     * Toggles the pause menu visibility and pauses or resumes the game.
     */
    public void togglePauseMenu() {
        if (isPaused) {
            resumeGame();
        } else {
            pauseGame();
        }
    }

    /**
     * Pauses the game and displays the pause menu.
     */
    private void pauseGame() {
        if (currentLevel == null) {
            logger.info("No level is currently active.");
            return;
        }
        isPaused = true;
        currentLevel.pauseGame(); // Pause the level logic
        pauseMenu.show(); // Display the pause menu
    }

    /**
     * Resumes the game and hides the pause menu.
     */
    private void resumeGame() {
        if (currentLevel == null) {
            logger.info("No level is currently active.");
            return;
        }
        isPaused = false;
        currentLevel.resumeGame(); // Resume the level logic
    }

    /**
     * Restarts the current level by delegating to the main controller.
     */
    private void restartCurrentLevel() {
        isPaused = false;
        controller.restartCurrentLevel();
    }

    /**
     * Restarts the game by delegating to the main controller.
     */
    private void restartGame() {
        isPaused = false;
        controller.restartGame();
    }

    /**
     * Returns to the main menu by delegating to the main controller.
     */
    private void goToMainMenu() {
        isPaused = false;
        controller.goToMainMenu();
    }
}
