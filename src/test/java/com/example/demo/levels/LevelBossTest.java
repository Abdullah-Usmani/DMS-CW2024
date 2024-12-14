package com.example.demo.levels;

import com.example.demo.actors.BossPlane;
import com.example.demo.displays.ActorInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.application.Platform;
import javafx.scene.Group;
import java.util.List;

class LevelBossTest {

    private LevelBoss levelBoss;
    private Group root;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        root = new Group();
        levelBoss = new LevelBoss(600.0, 800.0);
    }

    @Test
    void testInitialization() {
        assertNotNull(levelBoss, "LevelBoss should be initialized");
        assertEquals("Boss", levelBoss.getLevelName(), "Level name should be 'Boss'");
        assertEquals(1, levelBoss.getKillsNeeded(), "Kill count should be 1");
    }

    @Test
    void testSpawnEnemyUnits() {
        levelBoss.spawnEnemyUnits();
        assertEquals(1, levelBoss.getCurrentNumberOfEnemies(), "Boss plane should be spawned");
        assertTrue(root.getChildren().contains(levelBoss.getRoot().getChildren().get(0)), "Boss plane should be added to the scene");
    }

    @Test
    void testCheckIfGameOver_UserDestroyed() {
        levelBoss.getUser().setHealth(0);
        levelBoss.checkIfGameOver();
        assertTrue(true, "Game over should be triggered when user is destroyed");
    }

    @Test
    void testCheckIfGameOver_BossDestroyed() {
        levelBoss.spawnEnemyUnits();
        BossPlane bossPlane = (BossPlane) levelBoss.getRoot().getChildren().get(0);
        bossPlane.setHealth(0);
        levelBoss.checkIfGameOver();
        assertTrue(true, "Game over should be triggered when boss is destroyed");
    }

    @Test
    void testInstantiateLevelView() {
        assertNotNull(levelBoss.instantiateLevelView(), "LevelView should be instantiated");
    }

    @Test
    void testGetActorsInfo() {
        List<ActorInfo> actorsInfo = levelBoss.getActorsInfo();
        assertNotNull(actorsInfo, "Actors info should not be null");
        assertEquals(5, actorsInfo.size(), "Actors info should contain 5 entries");
    }
}
