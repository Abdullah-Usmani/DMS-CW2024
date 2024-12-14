package com.example.demo.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

class ControllerTest {

    private Controller controller;
    private Stage stage;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> {
            stage = new Stage();
            controller = new Controller(stage);
        });
    }

    @Test
    void testInitialization() {
        assertNotNull(controller, "Controller should be initialized");
        assertNotNull(controller.pauseMenuController, "PauseMenuController should be initialized");
    }

    @Test
    void testLaunchGame() {
        Platform.runLater(() ->
                assertDoesNotThrow(() -> controller.launchGame(), "Launching the game should not throw exceptions")
        );
    }

    @Test
    void testGoToLevel() {
        String testLevelClass = "com.example.demo.levels.LevelOne";
        Platform.runLater(() ->
                assertDoesNotThrow(() -> controller.goToLevel(testLevelClass), "Transitioning to a level should not throw exceptions")
        );
    }

    @Test
    void testGoToMainMenu() {
        Platform.runLater(() ->
                assertDoesNotThrow(() -> controller.goToMainMenu(), "Going to the main menu should not throw exceptions")
        );
    }

    @Test
    void testRestartGame() {
        Platform.runLater(() ->
                assertDoesNotThrow(() -> controller.restartGame(), "Restarting the game should not throw exceptions")
        );
    }

    @Test
    void testRestartCurrentLevel() {
        Platform.runLater(() ->
                assertDoesNotThrow(() -> controller.restartCurrentLevel(), "Restarting the current level should not throw exceptions")
        );
    }

    @Test
    void testHandleKeyPress() {
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "P", "P", KeyCode.P, false, false, false, false);
        Platform.runLater(() ->
                assertDoesNotThrow(() -> controller.handleKeyPress(keyEvent), "Handling key press should not throw exceptions")
        );
    }
}
