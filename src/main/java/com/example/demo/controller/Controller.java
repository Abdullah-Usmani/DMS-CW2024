package com.example.demo.controller;

import com.example.demo.levels.LevelParent;
import com.example.demo.menus.StartMenu;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Controller {
	private final Stage stage;
	private final PauseMenuController pauseMenuController;
	private LevelParent currentLevel;

	public Controller(Stage stage) {
		this.stage = stage;
		this.pauseMenuController = new PauseMenuController(stage, this);
	}

	public void launchGame() {
		try {
			goToLevel("com.example.demo.levels.LevelOne");
		} catch (Exception e) {
			System.err.println("Error launching the game: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void goToLevel(String className) throws Exception {
		// Instantiate the level dynamically
		LevelParent level = (LevelParent) Class.forName(className)
				.getConstructor(double.class, double.class)
				.newInstance(stage.getHeight(), stage.getWidth());

		// Set up the level
		this.currentLevel = level;
		pauseMenuController.setCurrentLevel(level);

		Scene scene = level.initializeScene();
		scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
		stage.setScene(scene);

		level.initializeLevel();
	}

	private void handleKeyPress(KeyEvent event) {
		if (event.getCode() == KeyCode.P) {
			pauseMenuController.togglePauseMenu();
		}
	}

	public void goToMainMenu() {
		StartMenu startMenu = new StartMenu(stage); // Pass the controller's launchGame method
		Scene menuScene = startMenu.initializeMenu();
		stage.setScene(menuScene);
	}

	// Method to restart the current level
    public void restartCurrentLevel() {
		if (currentLevel != null) {
			try {
				goToLevel(currentLevel.getClass().getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
