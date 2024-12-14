package com.example.demo.actors;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.Config;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectileTest {

    private Projectile projectile;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        projectile = new Projectile(Config.ENEMY_GUN, 10, 5, 100.0, 200.0, 20.0, 3);
    }

    @Test
    void testInitialization() {
        assertNotNull(projectile, "Projectile should be initialized");
        assertEquals(Config.ENEMY_GUN, projectile.getImageName(), "Image name should match");
        assertEquals(1, projectile.getHealth(), "Initial health should be 1");
        assertEquals(3, projectile.getDamage(), "Damage multiplier should match");
        assertEquals(20.0, projectile.horizontalVelocity, "Horizontal velocity should match");
    }

    @Test
    void testUpdatePosition() {
        double initialX = projectile.getTranslateX();
        projectile.updatePosition();
        assertEquals(initialX + 20.0, projectile.getTranslateX(), "Projectile should move correctly based on velocity");
    }

    @Test
    void testUpdateActor() {
        double initialX = projectile.getTranslateX();
        projectile.updateActor();
        assertEquals(initialX + 20.0, projectile.getTranslateX(), "Projectile's actor update should update position correctly");
    }

    @Test
    void testGetDamage() {
        assertEquals(3, projectile.getDamage(), "Projectile damage should match the multiplier");
    }

    @Test
    void testGetImageName() {
        assertEquals(Config.ENEMY_GUN, projectile.getImageName(), "Image name should match");
    }
}
