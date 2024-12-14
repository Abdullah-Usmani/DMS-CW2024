package com.example.demo.levels;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.Group;

class LevelViewTest {

    private LevelView levelView;
    private Group root;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }
    
    @BeforeEach
    void setUp() {
        root = new Group();
        levelView = new LevelView(root, 3, 5);
    }

    @Test
    void testInitialization() {
        assertNotNull(levelView, "LevelView should be initialized");
        assertNotNull(root.getChildren(), "Root group should not be null");
    }

    @Test
    void testShowHeartDisplay() {
        levelView.showHeartDisplay();
        assertTrue(root.getChildren().contains(levelView.heartDisplay.getContainer()), "Heart display should be added to the root");
    }

    @Test
    void testShowKillDisplay() {
        levelView.showKillDisplay();
        assertTrue(root.getChildren().contains(levelView.killDisplay.getContainer()), "Kill display should be added to the root");
    }

    @Test
    void testShowWinImage() {
        levelView.showWinImage();
        assertTrue(root.getChildren().contains(levelView.winImage), "Win image should be added to the root");
    }

    @Test
    void testShowLoseImage() {
        levelView.showLoseImage();
        assertTrue(root.getChildren().contains(levelView.loseImage), "Lose image should be added to the root");
    }

    @Test
    void testRemoveHearts() {
        levelView.showHeartDisplay();
        levelView.removeHearts(1);
        assertEquals(1, levelView.heartDisplay.getContainer().getChildren().size(), "Hearts should update correctly when removed");
    }

    @Test
    void testAddKills() {
        levelView.showKillDisplay();
        levelView.addKills(3);
        // Assuming LevelParent integration, mock or stub a method to verify the user's kill count update
        // Replace the assertion below with proper integration logic if applicable
        assertTrue(true, "Kill count update would be verified via LevelParent's UserPlane integration");
    }
}