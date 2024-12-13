/**
 * Displays the player's remaining hearts (lives) in the game "F-15: Strike Eagle."
 *
 * The HeartDisplay class provides a visual representation of the player's lives using heart icons.
 */
package com.example.demo.displays;

import com.example.demo.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * HeartDisplay manages the visual display of the player's remaining lives.
 */
public class HeartDisplay {

	/** The image name for the heart icon. */
	private static final String HEART_IMAGE_NAME = Config.HEART_IMAGE;

	/** The size of the heart icon. */
	private static final double HEART_SIZE = Config.HEART_SIZE;

	/** The container holding the heart icons. */
	private HBox container;

	/** The x-coordinate of the container. */
	private final double containerXPosition;

	/** The y-coordinate of the container. */
	private final double containerYPosition;

	/** The initial number of hearts to display. */
	private final int numberOfHeartsToDisplay;

	/**
	 * Constructs a HeartDisplay with specified position and initial heart count.
	 *
	 * @param xPosition       the x-coordinate of the display.
	 * @param yPosition       the y-coordinate of the display.
	 * @param heartsToDisplay the initial number of hearts to display.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container for the heart display.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes the heart icons in the display.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
			heart.setFitHeight(HEART_SIZE);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes one heart from the display, indicating a lost life.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty()) {
			container.getChildren().remove(0);
		}
	}

	/**
	 * Gets the container holding the heart icons.
	 *
	 * @return the container as an HBox.
	 */
	public HBox getContainer() {
		return container;
	}
}
