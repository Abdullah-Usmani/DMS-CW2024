package com.example.demo.menus;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EndMenuTest {

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @Test
    public void testMenuInitialization() {
        Platform.runLater(() -> {
            // Set up callbacks
            Runnable onExitToMainMenu = () -> {};
            Runnable onRestartGame = () -> {};
            Runnable onRestartLevel = () -> {};

            // Create the EndMenu
            EndMenu endMenu = new EndMenu(800, 600, onExitToMainMenu, onRestartGame, onRestartLevel);

            // Verify layout
            assertNotNull(endMenu, "EndMenu should not be null");
            assertEquals(800, endMenu.getPrefWidth(), 0.1, "EndMenu width should match the specified screen width");
            assertEquals(600, endMenu.getPrefHeight(), 0.1, "EndMenu height should match the specified screen height");

            VBox rootLayout = (VBox) endMenu.getChildren().get(0);
            assertNotNull(rootLayout, "Root layout should not be null");
            assertEquals(2, rootLayout.getChildren().size(), "Root layout should contain a spacer and menu");

            VBox menu = (VBox) rootLayout.getChildren().get(1);
            assertNotNull(menu, "Menu layout should not be null");
            assertEquals(3, menu.getChildren().size(), "Menu should contain 3 buttons");
        });
    }

    @Test
    public void testButtonActions() {
        Platform.runLater(() -> {
            // Set up mock flags for button actions
            boolean[] exitCalled = {false};
            boolean[] restartGameCalled = {false};
            boolean[] restartLevelCalled = {false};

            // Define button actions
            Runnable onExitToMainMenu = () -> exitCalled[0] = true;
            Runnable onRestartGame = () -> restartGameCalled[0] = true;
            Runnable onRestartLevel = () -> restartLevelCalled[0] = true;

            // Create the EndMenu
            EndMenu endMenu = new EndMenu(800, 600, onExitToMainMenu, onRestartGame, onRestartLevel);
            VBox menu = (VBox) ((VBox) endMenu.getChildren().get(0)).getChildren().get(1);

            // Verify "Restart Level" button
            Button restartLevelButton = (Button) menu.getChildren().get(0);
            assertNotNull(restartLevelButton, "Restart Level button should not be null");
            restartLevelButton.fire();
            assertTrue(restartLevelCalled[0], "Restart Level callback should be executed");

            // Verify "Restart Game" button
            Button restartGameButton = (Button) menu.getChildren().get(1);
            assertNotNull(restartGameButton, "Restart Game button should not be null");
            restartGameButton.fire();
            assertTrue(restartGameCalled[0], "Restart Game callback should be executed");

            // Verify "Exit to Main Menu" button
            Button exitButton = (Button) menu.getChildren().get(2);
            assertNotNull(exitButton, "Exit to Main Menu button should not be null");
            exitButton.fire();
            assertTrue(exitCalled[0], "Exit to Main Menu callback should be executed");
        });
    }
}
