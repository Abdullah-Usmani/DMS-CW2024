package com.example.demo.controller;

import com.example.demo.levels.LevelParent;
import com.example.demo.menus.PauseMenu;
import javafx.stage.Stage;

public class PauseMenuController {
    private final Stage stage;
    private final PauseMenu pauseMenu;
    private LevelParent currentLevel;
    private boolean isPaused;

    public PauseMenuController(Stage stage, Controller controller) {
        this.stage = stage;
        this.isPaused = false;
        this.pauseMenu = new PauseMenu(stage, this::resumeGame, controller::goToMainMenu, controller::restartCurrentLevel);
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
}
