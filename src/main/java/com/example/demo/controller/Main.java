package com.example.demo.controller;

import com.example.demo.Config;
import com.example.demo.menus.StartMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private static Stage primaryStage;

	public void start(Stage stage) {
		primaryStage = stage;
		primaryStage.setTitle("2042");

		// Load the Start Menu
		StartMenu startMenu = new StartMenu(primaryStage);
		Scene startScene = startMenu.initializeMenu();
		primaryStage.setScene(startScene);

		// Set initial size from Config
		primaryStage.setWidth(Config.getScreenWidth());
		primaryStage.setHeight(Config.getScreenHeight());
		primaryStage.show();
	}

	public static void restartGame() {
		// Close the current stage and reopen the application
		primaryStage.close();

		// Reinitialize the application
		new Main().start(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}

