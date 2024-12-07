package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import com.example.demo.*;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import com.example.demo.LevelParent;

public class Controller implements Observer {

	public static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final Stage stage;
	private PauseMenu pauseMenu;
	private LevelParent currentLevel;
	private boolean isPaused = false; // Track pause state
	private final Timeline timeline;

	public Controller(Stage stage) {
		this.stage = stage;
		initializePauseMenu();
		this.timeline = new Timeline();
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
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());

		// Configure the new level
		myLevel.addObserver(this); // Observe for level changes
		Scene scene = myLevel.initializeScene();
		scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);

		stage.setScene(scene);
		currentLevel = myLevel; // Set the current level reference
		myLevel.initializeLevel();

		// LevelParent handles its own start (e.g., showLevelOverlay or startGame)
		System.out.println("Level set. Overlay should display.");
	}

	// Pause the game
	private void pauseGame() {
		System.out.println("Game Paused");
		isPaused = true;
		if (currentLevel != null) {
			currentLevel.pauseGame(); // Stop game logic
		}
		pauseMenu.show();  // Show pause menu
	}

	// Resume the game
	private void resumeGame() {
		System.out.println("Game Resumed");
		isPaused = false;
		if (currentLevel != null) {
			currentLevel.resumeGame(); // Resume game logic
		}
		pauseMenu.hide();  // Hide pause menu
	}

//	// Handle restarting the game
//    public void restartGame() {
//		System.out.println("Game Restarting...");
//		timeline.play();
//		launchGame();  // Re-launch the game
//	}

	// Handle closing the game
	private void closeGame() {
		System.exit(0);  // Exit the game
	}

	private void handleKeyPress(KeyEvent event) {
		if (event.getCode() == KeyCode.P) {  // Pause/Unpause on pressing 'P'
			if (isPaused) {
				resumeGame();
			} else {
				pauseGame();
			}
		}
	}

	// Initialize the pause menu
	private void initializePauseMenu() {
		pauseMenu = new PauseMenu(stage,
				this::resumeGame,  // Resume game logic
				() -> System.exit(0), // Exit game logic
				() -> System.out.println("Settings Opened")); // Placeholder for settings logic
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
