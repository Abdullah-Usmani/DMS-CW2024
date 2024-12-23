/**
 * Entry point for the game "F-15: Strike Eagle."
 *
 * The Main class initializes the game application, sets up the primary stage,
 * and loads the start menu. It also provides functionality to restart the game.
 */
package com.example.demo.controller;

import com.example.demo.Config;
import com.example.demo.managers.AudioManager;
import com.example.demo.menus.StartMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main initializes the game application and manages its lifecycle.
 */
public class Main extends Application {

	/** The primary stage for the application. */
	private static Stage primaryStage;

	/**
	 * Starts the application by setting up the primary stage and loading the start menu.
	 *
	 * @param stage the primary stage for the application.
	 */
	@Override
	public void start(Stage stage) {
		primaryStage = stage;
		primaryStage.setTitle(Config.GAME_TITLE);
		primaryStage.setResizable(false);

		// Load the Start Menu
		StartMenu startMenu = new StartMenu(primaryStage);
		Scene startScene = startMenu.initializeMenu();
		primaryStage.setScene(startScene);

		// Set initial size from Config
		primaryStage.setWidth(Config.getScreenWidth());
		primaryStage.setHeight(Config.getScreenHeight());
		primaryStage.show();

		// Play background audio
		AudioManager.backgroundOST();
	}

	/**
	 * Restarts the game by reinitializing the application.
	 */
	public static void restartGame() {
		// Close the current stage and reopen the application
		primaryStage.close();

		// Reinitialize the application
		new Main().start(primaryStage);
	}

	/**
	 * The main method launches the game application.
	 *
	 * @param args command-line arguments (not used).
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
