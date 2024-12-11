package com.example.demo.controller;

import com.example.demo.levels.LevelParent;
import com.example.demo.menus.PauseMenu;
import javafx.stage.Stage;

public class PauseMenuController {
    private final PauseMenu pauseMenu;
    private LevelParent currentLevel;
    private final Controller controller;
    private boolean isPaused;

    public PauseMenuController(Stage stage, Controller controller) {
        this.isPaused = false;
        this.controller = controller;
        this.pauseMenu = new PauseMenu(stage, this::resumeGame, this::restartCurrentLevel, this::restartGame, this::goToMainMenu);
    }

    public void setCurrentLevel(LevelParent level) {
        this.currentLevel = level;
    }

    public void togglePauseMenu() {
        if (isPaused) {
            resumeGame();
        } else {
            pauseGame();
        }
    }

    private void pauseGame() {
        if (currentLevel == null) {
            System.err.println("No level is currently active.");
            return;
        }
        isPaused = true;
        currentLevel.pauseGame(); // Pause the level logic
        pauseMenu.show(); // Display the pause menu
    }

    private void resumeGame() {
        if (currentLevel == null) {
            System.err.println("No level is currently active.");
            return;
        }
        isPaused = false;
        currentLevel.resumeGame(); // Resume the level logic
    }

    private void restartCurrentLevel() {
        isPaused = false;
        controller.restartCurrentLevel();
    }

    private void restartGame() {
        isPaused = false;
        controller.restartGame();
    }

    private void goToMainMenu() {
        isPaused = false;
        controller.goToMainMenu();
    }
}
