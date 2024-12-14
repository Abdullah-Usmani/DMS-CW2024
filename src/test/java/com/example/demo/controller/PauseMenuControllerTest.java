package com.example.demo.controller;

import com.example.demo.Config;
import com.example.demo.displays.ActorInfo;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.LevelView;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class PauseMenuControllerTest {

    private static Stage stage;
    private TestController testController;
    private TestLevelParent testLevel;
    private PauseMenuController pauseMenuController;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Use a CountDownLatch to ensure the JavaFX thread completes setup before proceeding
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            stage = new Stage();
            testController = new TestController();
            testLevel = new TestLevelParent(Config.LEVEL2_BACKGROUND, 800, 600, 5);
            pauseMenuController = new PauseMenuController(stage, testController);
            latch.countDown(); // Signal that setup is complete
        });

        // Wait for the JavaFX thread to complete
        latch.await();
    }

    @Test
    public void testTogglePauseMenu_Pause() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            pauseMenuController.togglePauseMenu();
            latch.countDown();
        });

        latch.await(); // Wait for the JavaFX Application Thread to complete

        // Perform assertions
        assertTrue(pauseMenuController.isPaused(), "Game should be paused");
        assertTrue(stage.isShowing(), "Pause menu should be visible");
    }

    @Test
    public void testTogglePauseMenu_Resume() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        // First pause the game
        CountDownLatch finalLatch = latch;
        Platform.runLater(() -> {
            pauseMenuController.togglePauseMenu();
            finalLatch.countDown();
        });
        latch.await(); // Wait for the first action to complete

        // Reset the latch for the next action
        latch = new CountDownLatch(1);

        // Resume the game
        CountDownLatch finalLatch1 = latch;
        Platform.runLater(() -> {
            pauseMenuController.togglePauseMenu();
            finalLatch1.countDown();
        });
        latch.await(); // Wait for the JavaFX Application Thread to complete

        // Perform assertions
        assertFalse(pauseMenuController.isPaused(), "Game should be resumed");
        assertFalse(stage.isShowing(), "Pause menu should not be visible");
    }

    @Test
    public void testTogglePauseMenu_NoActiveLevel() {
        // Toggle without setting a level
        pauseMenuController.togglePauseMenu();

        // Verify that the game state doesn't change
        assertFalse(pauseMenuController.isPaused(), "Game should remain unpaused without an active level");
    }

    @Test
    public void testRestartCurrentLevel() {
        // Restart the current level
        pauseMenuController.restartCurrentLevel();

        // Verify the controller is called and the game is not paused
        assertFalse(pauseMenuController.isPaused(), "Game should not be paused after restarting the level");
        assertTrue(testController.currentLevelRestarted, "Current level should be restarted");
    }

    @Test
    public void testRestartGame() {
        // Restart the game
        pauseMenuController.restartGame();

        // Verify the controller is called and the game is not paused
        assertFalse(pauseMenuController.isPaused(), "Game should not be paused after restarting the game");
        assertTrue(testController.gameRestarted, "Game should be restarted");
    }

    @Test
    public void testGoToMainMenu() {
        // Return to the main menu
        pauseMenuController.goToMainMenu();

        // Verify the controller is called and the game is not paused
        assertFalse(pauseMenuController.isPaused(), "Game should not be paused after returning to the main menu");
        assertTrue(testController.mainMenuOpened, "Main menu should be opened");
    }

    // Test implementations for Controller and LevelParent

    private static class TestController extends Controller {
        boolean currentLevelRestarted = false;
        boolean gameRestarted = false;
        boolean mainMenuOpened = false;

        /**
         * Constructs a Controller for managing game flow and menus.
         */
        public TestController() {
            super(stage);
        }

        @Override
        public void restartCurrentLevel() {
            currentLevelRestarted = true;
        }

        @Override
        public void restartGame() {
            gameRestarted = true;
        }

        @Override
        public void goToMainMenu() {
            mainMenuOpened = true;
        }
    }

    private static class TestLevelParent extends LevelParent {
        private boolean paused = false;

        /**
         * Constructs a new LevelParent.
         *
         * @param backgroundImageName the name of the background image resource.
         * @param screenHeight        the height of the screen.
         * @param screenWidth         the width of the screen.
         * @param playerInitialHealth the initial health of the player's plane.
         */
        protected TestLevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
            super(backgroundImageName, screenHeight, screenWidth, playerInitialHealth);
        }

        @Override
        protected LevelView instantiateLevelView() {
            return null;
        }

        @Override
        protected void initializeFriendlyUnits() {

        }

        @Override
        protected void checkIfGameOver() {

        }

        @Override
        protected void spawnEnemyUnits() {

        }

        @Override
        protected String getLevelName() {
            return "";
        }

        @Override
        protected int getKillsNeeded() {
            return 0;
        }

        @Override
        protected List<ActorInfo> getActorsInfo() {
            return List.of();
        }

        @Override
        public void pauseGame() {
            paused = true;
        }

        @Override
        public void resumeGame() {
            paused = false;
        }

        public boolean isPaused() {
            return paused;
        }
    }
}
