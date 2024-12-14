package com.example.demo.displays;

import com.example.demo.Config;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StartOverlayTest {

    private Group root;
    private StartOverlay startOverlay;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX Toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        startOverlay = new StartOverlay(root, () -> System.out.println("Game Started"));
    }

    @Test
    void testShowLevelOverlay() {
        Platform.runLater(() -> {
            List<ActorInfo> actors = new ArrayList<>();
            actors.add(new ActorInfo("Friendly Plane", Config.USER_IMAGE, 3, true, true));
            actors.add(new ActorInfo("Enemy Plane", Config.ENEMY2_IMAGE, 5, true, false));

            startOverlay.showLevelOverlay("Level 1", actors, 10);

            assertNotNull(root.lookup(".label"), "Overlay should add labels to the scene");
        });
    }

    @Test
    void testCreateCategoryBox() {
        Platform.runLater(() -> {
            List<ActorInfo> actors = new ArrayList<>();
            actors.add(new ActorInfo("Friendly Projectile", Config.FRIENDLY_GUN, 7, false, true));
            VBox categoryBox = startOverlay.createCategoryBox("Test Category", actors);

            assertNotNull(categoryBox, "Category box should not be null");
            assertEquals(2, categoryBox.getChildren().size(), "Category box should have a title and one actor row");
        });
    }
}
