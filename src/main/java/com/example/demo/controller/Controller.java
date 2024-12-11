package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import com.example.demo.Config;
import com.example.demo.menus.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import com.example.demo.levels.LevelParent;

public class Controller implements Observer {

	public static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";
	private final Stage stage;
	private final PauseMenuController pauseMenuController;
	private LevelParent currentLevel;

	public Controller(Stage stage) {
		this.stage = stage;
        this.pauseMenuController = new PauseMenuController(stage,this);
        LevelParent.setController(this);
	}

	// Launch the first level explicitly
	public void launchGame() {
		try {
			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
		} catch (Exception e) {
			System.err.println("Error launching the game: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Transition to a new level
	public void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// Reflectively create the new level instance
		System.out.println("Transitioning to level: " + className);
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(Config.getScreenHeight(), Config.getScreenWidth());

		// Configure the new level
		myLevel.addObserver(this); // Observe for level changes
		Scene scene = myLevel.initializeScene();
		scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);

		stage.setScene(scene);
		currentLevel = myLevel; // Set the current level reference
		myLevel.initializeLevel();
		this.pauseMenuController.setCurrentLevel(currentLevel);
	}

	private void handleKeyPress(KeyEvent event) {
		if (event.getCode() == KeyCode.P) {
//			System.out.println("Handle P Press");
			pauseMenuController.togglePauseMenu();
		}
	}

	public void goToMainMenu() {
//		System.out.println("Main Menu Called");
		StartMenu startMenu = new StartMenu(stage); // Pass the controller's launchGame method
		Scene menuScene = startMenu.initializeMenu();
		stage.setScene(menuScene);
	}

	// Method to restart the current level
	public void restartGame() {
//		System.out.println("Restart Game called");
		try {
			goToLevel(LEVEL_ONE_CLASS_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	// Observer update logic for transitions and game end states
	@Override
	public void update(Observable observable, Object arg) {
		try {
			goToLevel((String) arg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
