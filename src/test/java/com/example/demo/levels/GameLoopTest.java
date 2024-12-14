package com.example.demo.levels;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.application.Platform;
import javafx.animation.Animation;

class GameLoopTest {

    private GameLoop gameLoop;
    private boolean actionExecuted;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        actionExecuted = false;
        gameLoop = new GameLoop(() -> actionExecuted = true, 100);
    }

    @Test
    void testInitialization() {
        assertNotNull(gameLoop, "GameLoop should be initialized");
    }

    @Test
    void testStart() throws InterruptedException {
        gameLoop.start();
        Thread.sleep(200); // Allow the loop to execute at least once
        assertTrue(actionExecuted, "GameLoop should execute the action when started");
        gameLoop.stop(); // Clean up after the test
    }

    @Test
    void testStop() throws InterruptedException {
        gameLoop.start();
        Thread.sleep(200); // Allow the loop to start executing
        gameLoop.stop();
        actionExecuted = false; // Reset the flag
        Thread.sleep(200); // Ensure no more executions after stopping
        assertFalse(actionExecuted, "GameLoop should not execute the action after being stopped");
    }

    @Test
    void testTimelineIndefiniteCycle() {
        gameLoop.start();
        assertEquals(Animation.INDEFINITE, gameLoop.timeline.getCycleCount(), "Timeline should have an indefinite cycle count");
        gameLoop.stop();
    }
}
