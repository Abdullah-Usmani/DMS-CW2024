/**
 * Displays the boss health in the game "F-15: Strike Eagle."
 *
 * The BossHealthDisplay class provides a visual representation of the boss's health,
 * using a heart icon and a counter label.
 */
package com.example.demo.displays;

import com.example.demo.Config;
import com.example.demo.managers.StyleManager;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * BossHealthDisplay manages the visual display of the boss's remaining health.
 */
public class BossHeartDisplay {

    /** The image name for the heart icon. */
    private static final String HEART_IMAGE_NAME = Config.HEART_IMAGE;

    /** The size of the heart icon. */
    private static final double HEART_SIZE = Config.HEART_SIZE;

    /** The spacing between elements in the display. */
    private static final double SPACING = Config.HEART_SPACING;

    /** The container holding the heart icon and health counter. */
    private final HBox container;

    /** The label displaying the boss's remaining health. */
    private final Label healthCounter;

    /** The current health of the boss. */
    private int health;

    /**
     * Constructs a BossHealthDisplay with specified position and initial health.
     *
     * @param xPosition    the x-coordinate of the display.
     * @param yPosition    the y-coordinate of the display.
     * @param initialHealth the initial health value of the boss.
     */
    public BossHeartDisplay(double xPosition, double yPosition, int initialHealth) {
        this.health = initialHealth;
        this.container = new HBox(SPACING);

        container.setLayoutX(xPosition);
        container.setLayoutY(yPosition);

        // Add heart image
        ImageView heartImage = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
        heartImage.setFitHeight(HEART_SIZE);
        heartImage.setFitWidth(HEART_SIZE);
        container.getChildren().add(heartImage);

        // Add health counter label
        this.healthCounter = StyleManager.createStyledLabel("x " + health, false, .025);
        healthCounter.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        container.getChildren().add(healthCounter);
    }

    /**
     * Updates the displayed health to the new value.
     *
     * @param newHealth the updated health value.
     */
    public void updateHealth(int newHealth) {
        this.health = newHealth;
        healthCounter.setText("x " + health);
    }

    /**
     * Gets the container holding the boss health display elements.
     *
     * @return the container as an HBox.
     */
    public HBox getContainer() {
        return container;
    }
}
