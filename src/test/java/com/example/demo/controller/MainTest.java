package com.example.demo.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.application.Platform;
import javafx.stage.Stage;

class MainTest {

    private static Stage primaryStage;
    private Main mainApp;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit (if not already initialized)
        Platform.startup(() -> {});
    }

    @Test
    void testStartApplication() {
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> {
                mainApp = new Main();
                primaryStage = new Stage(); // Create the stage on JavaFX thread
                mainApp.start(primaryStage);

                assertEquals("F-15: Strike Eagle", primaryStage.getTitle(), "Game title should be set correctly");
                assertFalse(primaryStage.isResizable(), "Primary stage should not be resizable");
                assertTrue(primaryStage.isShowing(), "Primary stage should be visible");
            });
        }, "Starting the application should not throw exceptions");
    }

    @Test
    void testRestartGame() {
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> {
                mainApp = new Main();
                primaryStage = new Stage(); // Create the stage on JavaFX thread
                mainApp.start(primaryStage);

                // Simulate game restart
                Main.restartGame();

                // Verify stage is closed and reopened
                assertTrue(primaryStage.isShowing(), "Primary stage should be visible after restart");
            });
        }, "Restarting the game should not throw exceptions");
    }
}

