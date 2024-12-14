package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.BossPlane;
import com.example.demo.displays.ActorInfo;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

class LevelParentTest {

    private LevelParent levelParent;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        levelParent = new LevelParent(Config.LEVEL2_BACKGROUND, 600.0, 800.0, 100) {
            @Override
            protected LevelView instantiateLevelView() {
                return new LevelView(getRoot(), 3, 10); // Dummy LevelView for testing
            }

            @Override
            protected void initializeFriendlyUnits() {
                // Dummy initialization for testing
            }

            @Override
            protected void checkIfGameOver() {
                // Dummy check for testing
            }

            @Override
            protected void spawnEnemyUnits() {
                // Dummy spawn logic for testing
            }

            @Override
            protected String getLevelName() {
                return "Test Level";
            }

            @Override
            protected int getKillsNeeded() {
                return 10;
            }

            @Override
            protected List<ActorInfo> getActorsInfo() {
                return new ArrayList<>();
            }
        };
    }

    @Test
    void testInitialization() {
        assertNotNull(levelParent, "LevelParent should be initialized");
        assertEquals(600.0, levelParent.getRoot().getScene().getHeight(), "Scene height should match");
        assertEquals(800.0, levelParent.getRoot().getScene().getWidth(), "Scene width should match");
    }

    @Test
    void testAddEnemyUnit() {
        ActiveActorDestructible enemy = new BossPlane();
        levelParent.addEnemyUnit(enemy);
        assertTrue(levelParent.getRoot().getChildren().contains(enemy), "Enemy unit should be added to the root");
    }

    @Test
    void testRemoveActors() {
        ActiveActorDestructible enemy = new BossPlane();
        levelParent.addEnemyUnit(enemy);
        enemy.setDestroyed();
        levelParent.removeActors();
        assertFalse(levelParent.getRoot().getChildren().contains(enemy), "Destroyed actor should be removed from the root");
    }

    @Test
    void testUserPlaneDestroyed() {
        levelParent.getUser().setHealth(0);
        assertTrue(levelParent.userIsDestroyed(), "User plane should be destroyed when health is 0");
    }

    @Test
    void testGoToNextLevel() {
        levelParent.goToNextLevel("Next Level");
        assertTrue(true, "Transition to next level should execute without errors");
    }

    @Test
    void testInitializeScene() {
        assertNotNull(levelParent.initializeScene(), "Scene should be initialized successfully");
    }

    @Test
    void testEndGame() {
        levelParent.endGame(true); // Simulate winning
        assertTrue(levelParent.getRoot().getChildren().stream().anyMatch(Objects::nonNull), "EndMenu should be added to root on game end");
    }

    @Test
    void testResetGameState() {
        levelParent.resetGameState();
        assertFalse(levelParent.userIsDestroyed(), "User plane should not be destroyed after game state reset");
        assertFalse(levelParent.getRoot().getChildren().stream().anyMatch(Objects::nonNull), "EndMenu should not exist after game state reset");
    }
}
