/**
 * The Controller class handles game flow and level transitions for "F-15: Strike Eagle."
 *
 * This class manages the initialization of levels, transitions between levels, and interactions
 * with the pause menu. It serves as the main controller for the game's functionality.
 */
package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import com.example.demo.Config;
import com.example.demo.managers.AudioManager;
import com.example.demo.menus.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import com.example.demo.levels.LevelParent;

/**
 * Controller manages the game's levels, transitions, and menus.
 */
public class Controller implements Observer {

	/** The class name for the first level. */
	public static final String LEVEL_ONE_CLASS_NAME = Config.LEVEL_BOSS_CLASS_NAME;

	/** The primary stage for the application. */
	private final Stage stage;

	/** The controller for the pause menu. */
	final PauseMenuController pauseMenuController;

	/** The currently active level. */
	private LevelParent currentLevel;

	/** Logger for debugging and error tracking. */
	Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * Constructs a Controller for managing game flow and menus.
	 *
	 * @param stage the primary stage for the application.
	 */
	public Controller(Stage stage) {
		this.stage = stage;
		this.pauseMenuController = new PauseMenuController(stage, this);
		LevelParent.setController(this);
	}

	/**
	 * Launches the first level of the game and starts background audio.
	 */
	public void launchGame() {
		try {
			stage.show();
			AudioManager.startAudio();
			goToLevel(LEVEL_ONE_CLASS_NAME);
		} catch (Exception e) {
			logger.info("Error launching the game: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Transitions to a specified level.
	 *
	 * @param className the fully qualified class name of the level to load.
	 * @throws ClassNotFoundException   if the class cannot be found.
	 * @throws NoSuchMethodException    if the constructor is not found.
	 * @throws SecurityException        if access to the constructor is denied.
	 * @throws InstantiationException   if the level instance cannot be created.
	 * @throws IllegalAccessException   if the constructor is inaccessible.
	 * @throws IllegalArgumentException if invalid arguments are passed.
	 * @throws InvocationTargetException if the constructor invocation fails.
	 */
	public void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// Reflectively create the new level instance
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

	/**
	 * Handles key press events, toggling the pause menu on 'P'.
	 *
	 * @param event the key event to handle.
	 */
    void handleKeyPress(KeyEvent event) {
		if (event.getCode() == KeyCode.P) {
			pauseMenuController.togglePauseMenu();
		}
	}

	/**
	 * Transitions to the main menu.
	 */
	public void goToMainMenu() {
		StartMenu startMenu = new StartMenu(stage);
		Scene menuScene = startMenu.initializeMenu();
		stage.setScene(menuScene);
	}

	/**
	 * Restarts the game by transitioning to the first level.
	 */
	public void restartGame() {
		try {
			goToLevel(LEVEL_ONE_CLASS_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Restarts the current level if one is active.
	 */
	public void restartCurrentLevel() {
		if (currentLevel != null) {
			try {
				goToLevel(currentLevel.getClass().getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Updates the controller in response to level changes or game state changes.
	 *
	 * @param observable the observable object.
	 * @param arg        the argument passed by the observable.
	 */
	@Override
	public void update(Observable observable, Object arg) {
		try {
			goToLevel((String) arg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
