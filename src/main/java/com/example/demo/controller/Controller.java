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

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final Stage stage;
//	private final LevelView levelView;
	private PauseMenu pauseMenu;
	private LevelParent currentLevel;
	private boolean isPaused = false; // Track pause state
	private final EndGameScreen endGameScreen;
	private final Timeline timeline;
	private LevelTwo levelTwo;

	public Controller(Stage stage) {
		this.stage = stage;
//		this.levelView = levelView;
		initializePauseMenu();
		this.endGameScreen = new EndGameScreen(stage.getWidth(), stage.getHeight(), this::restartGame, this::closeGame);
		this.timeline = new Timeline();
	}

	public void launchGame() {
		try {
			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException |
				 InstantiationException | IllegalAccessException | IllegalArgumentException |
				 InvocationTargetException e) {
			// Handle exceptions gracefully, log, or show an error to the user
			System.err.println("Error launching the game: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Initialize the pause menu
	private void initializePauseMenu() {
		pauseMenu = new PauseMenu(stage,
				this::resumeGame,  // Resume game logic
				() -> System.exit(0), // Exit game logic
				() -> System.out.println("Settings Opened")); // Placeholder for settings logic
	}

	// Transition to a new level
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException,

			InstantiationException, IllegalAccessException, InvocationTargetException {
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
			myLevel.addObserver(this); // Add Observer for game state changes
			Scene scene = myLevel.initializeScene();
			// Register the key press event handler for pause functionality
			scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
			stage.setScene(scene);  // Set the scene for this level
			myLevel.startGame();  // Start the game
			currentLevel = myLevel; // Set the current level reference
		}
	// Handle key press events for pause functionality

	private void handleKeyPress(KeyEvent event) {
		if (event.getCode() == KeyCode.P) {  // Pause/Unpause on pressing 'P'
			if (isPaused) {
				resumeGame();
			} else {
				pauseGame();
			}
		}
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

	// Observer update logic for transitions and game end states
	@Override
	public void update(Observable observable, Object arg) {
		if ("GAME_OVER".equals(arg)) {
			// Show the end game screen
			endGameScreen.start(stage);
		} else if (arg instanceof String) {
			// Handle transitioning to the next level
			try {
				goToLevel((String) arg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Handle restarting the game
	private void restartGame() {
		System.out.println("Game Restarting...");
//		// Logic to reset all game state
//		levelView.removeHearts(3); // Example: Reset hearts
//		levelView.updateKills(0);  // Reset kill count
//		levelView.showHeartDisplay(); // Show heart display again
//		levelView.showKillDisplay(); // Show kill display again
//		levelView.showBossHealth();
		timeline.play();
		launchGame();  // Re-launch the game

	}

	// Handle closing the game
	private void closeGame() {
		System.exit(0);  // Exit the game
	}
}
