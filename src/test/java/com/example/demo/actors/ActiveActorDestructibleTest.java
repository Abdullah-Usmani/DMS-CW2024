package com.example.demo.actors;

import com.example.demo.Config;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActiveActorDestructibleTest {

    private ActiveActorDestructible actor;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        actor = new ActiveActorDestructible(Config.USER_IMAGE, 50, 50, 100.0, 200.0, 100) {
            @Override
            public void updateActor() {
                // Dummy implementation for testing purposes
            }
        };
    }

    @Test
    void testInitialization() {
        assertNotNull(actor, "Actor should be initialized");
        assertEquals(100, actor.getHealth(), "Initial health should match");
        assertEquals(Config.USER_IMAGE, actor.getImageName(), "Image name should match");
        assertFalse(actor.isDestroyed(), "Actor should not be destroyed initially");
    }

    @Test
    void testTakeDamage() {
        actor.takeDamage(30);
        assertEquals(70, actor.getHealth(), "Health should decrease correctly");

        actor.takeDamage(80);
        assertTrue(actor.isDestroyed(), "Actor should be destroyed when health drops to 0 or below");
    }

    @Test
    void testIsOutOfBounds() {
        assertFalse(actor.isOutOfBounds(100.0, 500.0), "Actor should be within bounds");
        assertTrue(actor.isOutOfBounds(-10.0, 500.0), "Actor should be out of bounds on the left");
        assertTrue(actor.isOutOfBounds(510.0, 500.0), "Actor should be out of bounds on the right");
    }

    @Test
    void testDestroy() {
        actor.destroy();
        assertTrue(actor.isDestroyed(), "Actor should be marked as destroyed");
    }

    @Test
    void testSetHealth() {
        actor.setHealth(50);
        assertEquals(50, actor.getHealth(), "Health should be updated correctly");
    }

    @Test
    void testUpdatePosition() {
        // Ensure actor is destroyed when it moves out of bounds
        actor.setTranslateX(1000); // Simulate position update out of bounds
        actor.updatePosition();
        assertTrue(actor.isDestroyed(), "Actor should be destroyed if it moves out of bounds");
    }
}
