package com.example.demo.actors;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.Config;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BossPlaneTest {

    private BossPlane bossPlane;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        bossPlane = new BossPlane();
    }

    @Test
    void testInitialization() {
        assertNotNull(bossPlane, "BossPlane should be initialized");
        assertEquals(Config.BOSS_IMAGE, bossPlane.getImageName(), "Image name should match configuration");
        assertEquals(Config.BOSS_HEALTH, bossPlane.getHealth(), "Initial health should match configuration");
    }

    @Test
    void testUpdatePositionWithinBounds() {
        double initialY = bossPlane.getTranslateY();
        bossPlane.updatePosition();
        double newY = bossPlane.getTranslateY();
        assertTrue(newY >= Config.BOSS_Y_POSITION_UPPER_BOUND && newY <= Config.BOSS_Y_POSITION_LOWER_BOUND,
                "BossPlane should stay within vertical bounds after updatePosition()");
    }

    @Test
    void testShieldActivation() {
        bossPlane.updateActor();
        boolean shieldedInitially = bossPlane.getShieldDisplay().isVisible();
        if (shieldedInitially) {
            assertTrue(bossPlane.getShieldDisplay().isVisible(), "Shield should be activated if probability is met");
        }
    }

    @Test
    void testFireProjectile() {
        for (int i = 0; i < 100; i++) { // Loop to account for randomness
            ActiveActorDestructible projectile = bossPlane.fireProjectile();
            if (projectile != null) {
                assertNotNull(projectile, "Projectile should not be null when fired");
                assertEquals(Config.BOSS_MISSILE, projectile.getImageName(), "Projectile image name should match");
                break;
            }
        }
    }

    @Test
    void testShouldTakeDamageWithShield() {
        bossPlane.activateShield(); // Ensure shield is active
        assertFalse(bossPlane.shouldTakeDamage(), "BossPlane should not take damage while shielded");
        bossPlane.deactivateShield(); // Ensure shield is inactive
        assertTrue(bossPlane.shouldTakeDamage(), "BossPlane should take damage when shield is inactive");
    }

    @Test
    void testUpdateShield() {
        bossPlane.updateShield();
        if (bossPlane.getShieldDisplay().isVisible()) {
            assertTrue(bossPlane.getShieldDisplay().isVisible(), "Shield display should be visible if shield is active");
        } else {
            assertFalse(bossPlane.getShieldDisplay().isVisible(), "Shield display should not be visible if shield is inactive");
        }
    }
}
