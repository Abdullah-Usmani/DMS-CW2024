package com.example.demo.menus;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PauseMenuTest {

    private boolean resumeCalled;
    private boolean restartLevelCalled;
    private boolean restartGameCalled;
    private boolean exitToMainMenuCalled;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @Test
    public void testMenuInitialization() {
        Platform.runLater(() -> {
            // Initialize callbacks
            Runnable onResume = () -> {};
            Runnable onRestartLevel = () -> {};
            Runnable onRestartGame = () -> {};
            Runnable onExitToMainMenu = () -> {};

            // Create the PauseMenu
            Stage primaryStage = new Stage();
            PauseMenu pauseMenu = new PauseMenu(primaryStage, onResume, onRestartLevel, onRestartGame, onExitToMainMenu);

            // Verify the PauseMenu structure
            assertNotNull(pauseMenu, "PauseMenu should not be null");
            StackPane root = (StackPane) pauseMenu.getChildrenUnmodifiable().get(0);
            VBox menu = (VBox) root.getChildren().get(1);

            assertNotNull(menu, "Menu layout should not be null");
            assertEquals(5, menu.getChildren().size(), "Menu should contain a title and 4 buttons");

            // Verify buttons
            Button playButton = (Button) menu.getChildren().get(1);
            Button restartLevelButton = (Button) menu.getChildren().get(2);
            Button restartGameButton = (Button) menu.getChildren().get(3);
            Button exitToMainMenuButton = (Button) menu.getChildren().get(4);

            assertNotNull(playButton, "Play button should not be null");
            assertNotNull(restartLevelButton, "Restart Level button should not be null");
            assertNotNull(restartGameButton, "Restart Game button should not be null");
            assertNotNull(exitToMainMenuButton, "Exit to Main Menu button should not be null");
        });
    }

    @Test
    public void testResumeButtonAction() {
        Platform.runLater(() -> {
            // Set up resume callback
            resumeCalled = false;
            Runnable onResume = () -> resumeCalled = true;

            PauseMenu pauseMenu = new PauseMenu(new Stage(), onResume, null, null, null);
            VBox menu = (VBox) ((StackPane) pauseMenu.getChildrenUnmodifiable().get(0)).getChildren().get(1);
            Button playButton = (Button) menu.getChildren().get(1);

            // Simulate button click
            playButton.fire();

            assertTrue(resumeCalled, "Resume callback should be executed");
        });
    }

    @Test
    public void testRestartLevelButtonAction() {
        Platform.runLater(() -> {
            // Set up restart level callback
            restartLevelCalled = false;
            Runnable onRestartLevel = () -> restartLevelCalled = true;

            PauseMenu pauseMenu = new PauseMenu(new Stage(), null, onRestartLevel, null, null);
            VBox menu = (VBox) ((StackPane) pauseMenu.getChildrenUnmodifiable().get(0)).getChildren().get(1);
            Button restartLevelButton = (Button) menu.getChildren().get(2);

            // Simulate button click
            restartLevelButton.fire();

            assertTrue(restartLevelCalled, "Restart Level callback should be executed");
        });
    }

    @Test
    public void testRestartGameButtonAction() {
        Platform.runLater(() -> {
            // Set up restart game callback
            restartGameCalled = false;
            Runnable onRestartGame = () -> restartGameCalled = true;

            PauseMenu pauseMenu = new PauseMenu(new Stage(), null, null, onRestartGame, null);
            VBox menu = (VBox) ((StackPane) pauseMenu.getChildrenUnmodifiable().get(0)).getChildren().get(1);
            Button restartGameButton = (Button) menu.getChildren().get(3);

            // Simulate button click
            restartGameButton.fire();

            assertTrue(restartGameCalled, "Restart Game callback should be executed");
        });
    }

    @Test
    public void testExitToMainMenuButtonAction() {
        Platform.runLater(() -> {
            // Set up exit to main menu callback
            exitToMainMenuCalled = false;
            Runnable onExitToMainMenu = () -> exitToMainMenuCalled = true;

            PauseMenu pauseMenu = new PauseMenu(new Stage(), null, null, null, onExitToMainMenu);
            VBox menu = (VBox) ((StackPane) pauseMenu.getChildrenUnmodifiable().get(0)).getChildren().get(1);
            Button exitToMainMenuButton = (Button) menu.getChildren().get(4);

            // Simulate button click
            exitToMainMenuButton.fire();

            assertTrue(exitToMainMenuCalled, "Exit to Main Menu callback should be executed");
        });
    }

    @Test
    public void testResumeOnKeyPress() {
        Platform.runLater(() -> {
            // Set up resume callback
            resumeCalled = false;
            Runnable onResume = () -> resumeCalled = true;

            PauseMenu pauseMenu = new PauseMenu(new Stage(), onResume, null, null, null);
            Scene pauseScene = pauseMenu.getScene();

            // Simulate pressing the "P" key
            pauseScene.getOnKeyPressed().handle(new javafx.scene.input.KeyEvent(
                    javafx.scene.input.KeyEvent.KEY_PRESSED, "", "", KeyCode.P, false, false, false, false));

            assertTrue(resumeCalled, "Resume callback should be executed on pressing 'P'");
        });
    }
}
