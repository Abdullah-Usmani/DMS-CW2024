package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ConfigTest {

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @Test
    void testNoInstantiation() {
        assertThrows(IllegalStateException.class, Config::new, "Config class should not be instantiated");
    }

    @Test
    void testDefaultScreenDimensions() {
        assertEquals(1280, Config.getScreenWidth(), "Default screen width should be 1280");
        assertEquals(720, Config.getScreenHeight(), "Default screen height should be 720");
    }

    @Test
    void testSetResolution() {
        int newWidth = 1920;
        int newHeight = 1080;

        Config.setResolution(newWidth, newHeight);

        assertEquals(newWidth, Config.getScreenWidth(), "Screen width should update correctly");
        assertEquals(newHeight, Config.getScreenHeight(), "Screen height should update correctly");
        assertEquals(newHeight * 0.015, Config.USER_VERTICAL_VELOCITY, 0.001, "User vertical velocity should be recalculated");
    }

    @Test
    void testFirstRunFlag() {
        assertTrue(Config.isFirstRun(), "isFirstRun should be true by default");

        Config.setFirstRun(false);

        assertFalse(Config.isFirstRun(), "isFirstRun should be false after being set");
    }

    @Test
    void testResolutionDependentValuesCalculation() {
        int width = 1600;
        int height = 900;

        Config.setResolution(width, height);

        assertEquals(width * 0.04, Config.HEART_SIZE, 0.001, "Heart size should adjust to screen width");
        assertEquals(height * 0.015, Config.USER_VERTICAL_VELOCITY, 0.001, "User vertical velocity should adjust to screen height");
        assertEquals((int) (height * 0.2), Config.BOSS_IMAGE_HEIGHT, "Boss image height should adjust to screen height");
        assertEquals((int) (width * 0.2), Config.BOSS_IMAGE_WIDTH, "Boss image width should adjust to screen width");
    }
}
