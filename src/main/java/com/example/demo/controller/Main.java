package com.example.demo.controller;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.example.demo.menus.StartMenu;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
	private Stage primaryStage;
	private final Map<String, int[]> resolutionMap = new HashMap<>();
	private static int currentWidth = 800;  // Default width
	private static int currentHeight = 600; // Default height

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		// Populate resolution map
		resolutionMap.put("800x600", new int[]{800, 600});
		resolutionMap.put("1024x768", new int[]{1024, 768});
		resolutionMap.put("1280x720", new int[]{1280, 720});
		resolutionMap.put("1920x1080", new int[]{1920, 1080});

		// Initialize StartMenu with a callback to update resolution
		StartMenu startMenu = new StartMenu(primaryStage, this::updateResolution);
		startMenu.showMenu();
	}

	// Method to update the resolution dynamically
	public void updateResolution(String resolution) {
		int[] dimensions = resolutionMap.get(resolution);
		if (dimensions != null) {
			currentWidth = dimensions[0];
			currentHeight = dimensions[1];

			// Set new width and height
			primaryStage.setWidth(currentWidth);
			primaryStage.setHeight(currentHeight);

			// Center the stage on the screen
			centerStage();

			System.out.println("Resolution updated to: " + resolution);
		} else {
			System.err.println("Invalid resolution: " + resolution);
		}
	}

	// Center the stage on the screen
	private void centerStage() {
		Screen screen = Screen.getPrimary();
		int screenWidth = (int) screen.getVisualBounds().getWidth();
		int screenHeight = (int) screen.getVisualBounds().getHeight();

		int newX = (screenWidth - currentWidth) / 2;
		int newY = (screenHeight - currentHeight) / 2;

		primaryStage.setX(newX);
		primaryStage.setY(newY);

		System.out.println("Window centered at X: " + newX + ", Y: " + newY);
	}

	// Get current screen width
	public static int getScreenWidth() {
		return currentWidth;
	}

	// Get current screen height
	public static int getScreenHeight() {
		return currentHeight;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
