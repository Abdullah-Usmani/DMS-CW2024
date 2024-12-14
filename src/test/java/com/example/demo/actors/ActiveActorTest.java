package com.example.demo.actors;

import com.example.demo.Config;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActiveActorTest {

    private ActiveActor activeActor;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        // Create a concrete subclass of ActiveActor for testing
        activeActor = new ActiveActor(Config.USER_IMAGE, 50, 50, 100.0, 200.0) {
            @Override
            public void updatePosition() {
                // Dummy implementation for testing purposes
            }
        };
    }

    @Test
    void testInitialization() {
        // Check initial values
        assertNotNull(activeActor, "ActiveActor should be initialized");
        assertEquals(100.0, activeActor.getLayoutX(), "Initial X position should match");
        assertEquals(200.0, activeActor.getLayoutY(), "Initial Y position should match");
        assertEquals(50, activeActor.getFitHeight(), "Image height should match");
    }

    @Test
    void testMoveHorizontally() {
        // Test horizontal movement
        activeActor.moveHorizontally(20.0);
        assertEquals(20.0, activeActor.getTranslateX(), "Horizontal position should update correctly");
    }

    @Test
    void testMoveVertically() {
        // Test vertical movement
        activeActor.moveVertically(-15.0);
        assertEquals(-15.0, activeActor.getTranslateY(), "Vertical position should update correctly");
    }

    @Test
    void testUpdatePosition() {
        // Test abstract method (dummy implementation in this case)
        activeActor.updatePosition();
        // Add assertions if necessary once the implementation is defined
        assertTrue(true, "updatePosition() should execute without errors");
    }
}
