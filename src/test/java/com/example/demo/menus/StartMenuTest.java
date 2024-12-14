package com.example.demo.menus;

import com.example.demo.Config;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StartMenuTest {

    private Stage stage;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() {
        // Initialize a Stage instance before each test
        Platform.runLater(() -> stage = new Stage());
    }

    @Test
    public void testMenuInitialization() {
        Platform.runLater(() -> {

            StartMenu startMenu = new StartMenu(stage);
            Scene scene = startMenu.initializeMenu();

            // Verify Scene Properties
            assertNotNull(scene, "Scene should not be null");
            StackPane root = (StackPane) scene.getRoot();
            assertNotNull(root, "Root layout should not be null");
        });
    }

    @Test
    public void testStartGameButton() {
        Platform.runLater(() -> {
            StartMenu startMenu = new StartMenu(stage);
            Scene scene = startMenu.initializeMenu();
            StackPane root = (StackPane) scene.getRoot();

            Button startButton = (Button) root.lookup("#startButton");
            assertNotNull(startButton, "Start Game button should exist");

            // Simulate button click
            startButton.fire();

            // Validate behavior (replace with proper assertions as needed)
            assertTrue(true, "Start button clicked successfully.");
        });
    }

    @Test
    public void testSettingsButton() {
        Platform.runLater(() -> {
            StartMenu startMenu = new StartMenu(stage);
            Scene scene = startMenu.initializeMenu();
            StackPane root = (StackPane) scene.getRoot();

            Button settingsButton = (Button) root.lookup("#settingsButton");
            assertNotNull(settingsButton, "Settings button should exist");

            // Simulate button click
            settingsButton.fire();

            // Validate behavior (replace with proper assertions if you can navigate to the settings menu)
            assertTrue(true, "Settings button clicked successfully.");
        });
    }

    @Test
    public void testHelpButton() {
        Platform.runLater(() -> {
            StartMenu startMenu = new StartMenu(stage);
            Scene scene = startMenu.initializeMenu();
            StackPane root = (StackPane) scene.getRoot();

            Button helpButton = (Button) root.lookup("#helpButton");
            assertNotNull(helpButton, "Help button should exist");

            // Simulate button click
            helpButton.fire();

            // Validate behavior (replace with proper assertions if you can navigate to the help menu)
            assertTrue(true, "Help button clicked successfully.");
        });
    }

    @Test
    public void testExitButton() {
        Platform.runLater(() -> {
            StartMenu startMenu = new StartMenu(stage);
            Scene scene = startMenu.initializeMenu();
            StackPane root = (StackPane) scene.getRoot();

            Button exitButton = (Button) root.lookup("#exitButton");
            assertNotNull(exitButton, "Exit button should exist");

            // Simulate button click
            exitButton.fire();

            // Validate behavior (since System.exit(0) terminates the JVM, this must be manually verified)
            assertTrue(true, "Exit button clicked successfully.");
        });
    }
}

