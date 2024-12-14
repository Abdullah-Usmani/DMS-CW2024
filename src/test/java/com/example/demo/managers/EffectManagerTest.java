package com.example.demo.managers;

import com.example.demo.Config;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EffectManagerTest {

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit, as it is required for testing JavaFX components
        Platform.startup(() -> {});
    }

    @Test
    public void testCreateSparkEffect() {
        // Stub Config values for testing
        Config.SPARK_SIZE = 50.0;

        Group root = new Group();
        EffectManager effectManager = new EffectManager();

        // Call the method
        effectManager.createSparkEffect(100, 200, root);

        // Verify that the effect is added to the root group
        assertEquals(1, root.getChildren().size());
        assertTrue(root.getChildren().get(0) instanceof ImageView);

        // Verify the ImageView properties
        ImageView effectImage = (ImageView) root.getChildren().get(0);
        assertEquals(100, effectImage.getLayoutX());
        assertEquals(200, effectImage.getLayoutY());
        assertEquals(Config.SPARK_SIZE, effectImage.getFitWidth());
        assertEquals(Config.SPARK_SIZE, effectImage.getFitHeight());
    }

    @Test
    public void testCreateDestructionEffect() {
        // Stub Config values for testing
        Config.EXPLOSION_SIZE = 100.0;

        Group root = new Group();
        EffectManager effectManager = new EffectManager();

        // Call the method
        effectManager.createDestructionEffect(300, 400, root);

        // Verify that the effect is added to the root group
        assertEquals(1, root.getChildren().size());
        assertTrue(root.getChildren().get(0) instanceof ImageView);

        // Verify the ImageView properties
        ImageView effectImage = (ImageView) root.getChildren().get(0);
        assertEquals(300, effectImage.getLayoutX());
        assertEquals(400, effectImage.getLayoutY());
        assertEquals(Config.EXPLOSION_SIZE, effectImage.getFitWidth());
        assertEquals(Config.EXPLOSION_SIZE, effectImage.getFitHeight());
    }

    @Test
    public void testEffectRemovalAfterFade() throws InterruptedException {
        // Stub Config values for testing
        Config.SPARK_SIZE = 50.0;

        Group root = new Group();
        EffectManager effectManager = new EffectManager();

        // Call the method
        effectManager.createSparkEffect(150, 250, root);

        // Verify that the effect is added to the root group
        assertEquals(1, root.getChildren().size());

        // Wait for fade-out duration to complete
        Thread.sleep(Config.SPARK_DURATION * 1000 + 500); // Add a small buffer

        // Verify that the effect is removed from the root group
        assertEquals(0, root.getChildren().size());
    }
}
