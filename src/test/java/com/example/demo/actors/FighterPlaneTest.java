package com.example.demo.actors;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.Config;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FighterPlaneTest {

    private FighterPlane fighterPlane;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        fighterPlane = new FighterPlane(Config.USER_IMAGE, 60, 80, 150.0, 250.0, 200) {
            @Override
            public void updateActor() {
                // Dummy implementation for testing
            }

            @Override
            public ActiveActorDestructible fireProjectile() {
                return new ActiveActorDestructible(Config.FRIENDLY_GUN, 10, 10, 0.0, 0.0, 1) {
                    @Override
                    public void updateActor() {
                        // Dummy implementation for testing
                    }
                };
            }
        };
    }

    @Test
    void testInitialization() {
        assertNotNull(fighterPlane, "FighterPlane should be initialized");
        assertEquals(200, fighterPlane.getHealth(), "Initial health should match");
        assertEquals(Config.USER_IMAGE, fighterPlane.getImageName(), "Image name should match");
    }

    @Test
    void testFireProjectile() {
        ActiveActorDestructible projectile = fighterPlane.fireProjectile();
        assertNotNull(projectile, "Projectile should not be null");
        assertEquals(Config.FRIENDLY_GUN, projectile.getImageName(), "Projectile image name should match");
    }

    @Test
    void testGetProjectileXPosition() {
        double projectileX = fighterPlane.getProjectileXPosition();
        assertEquals(150.0 + fighterPlane.getProjectileXOffset(), projectileX, "Projectile X position should be calculated correctly");
    }

    @Test
    void testGetProjectileYPosition() {
        double projectileY = fighterPlane.getProjectileYPosition();
        assertEquals(250.0 + (60 / 2.0), projectileY, "Projectile Y position should be calculated correctly");
    }

    @Test
    void testPlayFiringAudio() {
        assertDoesNotThrow(() -> fighterPlane.playFiringAudio(Config.FRIENDLY_GUN_AUDIO), "Playing firing audio should not throw an exception");
    }

    @Test
    void testGetProjectileXOffset() {
        double offset = fighterPlane.getProjectileXOffset();
        assertEquals(-80.0, offset, "Projectile X offset should be calculated correctly for a generic fighter plane");
    }
}
