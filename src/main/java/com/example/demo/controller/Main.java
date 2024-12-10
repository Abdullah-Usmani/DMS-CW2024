package com.example.demo.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.example.demo.menus.StartMenu;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		// Initialize StartMenu
		StartMenu startMenu = new StartMenu(primaryStage);
		Scene startScene = startMenu.initializeMenu();
		primaryStage.setScene(startScene);
		primaryStage.setTitle("2042");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
