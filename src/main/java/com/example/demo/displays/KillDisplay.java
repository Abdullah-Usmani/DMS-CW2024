/**
 * Displays the player's kill count in the game "F-15: Strike Eagle."
 *
 * The KillDisplay class provides a visual representation of the player's current kills
 * and the total kills needed to complete the level.
 */
package com.example.demo.displays;

import com.example.demo.Config;
import com.example.demo.managers.StyleManager;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * KillDisplay manages the visual display of the player's kill count and required kills.
 */
public class KillDisplay {

	/** The image name for the kill icon. */
	private static final String KILL_IMAGE_NAME = Config.KILL_IMAGE;

	/** The height of the kill icon. */
	private static final double KILL_HEIGHT = Config.KILL_SIZE;

	/** The container holding the kill icon and counter. */
	private HBox container;

	/** The x-coordinate of the container. */
	private final double containerXPosition;

	/** The y-coordinate of the container. */
	private final double containerYPosition;

	/** The label displaying the kill count. */
	private Label killCounter;

	/** The total kills required to complete the level. */
	private final int killsNeeded;

	/**
	 * Constructs a KillDisplay with specified position and required kills.
	 *
	 * @param xPosition    the x-coordinate of the display.
	 * @param yPosition    the y-coordinate of the display.
	 * @param killsNeeded  the total kills required to complete the level.
	 */
	public KillDisplay(double xPosition, double yPosition, int killsNeeded) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.killsNeeded = killsNeeded;
		initializeContainer();
		initializeKills();
	}

	/**
	 * Initializes the container for the kill display.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
		container.setSpacing(10); // Adds spacing between elements
		container.setAlignment(Pos.CENTER_LEFT);
	}

	/**
	 * Initializes the kill icon and counter label in the display.
	 */
	private void initializeKills() {
		// Kill Image
		ImageView kill = new ImageView(new Image(getClass().getResource(KILL_IMAGE_NAME).toExternalForm()));
		kill.setFitHeight(KILL_HEIGHT);
		kill.setPreserveRatio(true);

		// Kill Counter Label
		killCounter = StyleManager.createStyledLabel("0 / " + killsNeeded, true, 0.025); // Initial kill count
		killCounter.setStyle("-fx-alignment: center-left;");

		// Add the image and label to the container
		container.getChildren().addAll(kill, killCounter);
	}

	/**
	 * Updates the displayed kill count.
	 *
	 * @param killCount the updated kill count.
	 */
	public void updateKills(int killCount) {
		killCounter.setText(killCount + " / " + killsNeeded);
	}

	/**
	 * Gets the container holding the kill display elements.
	 *
	 * @return the container as an HBox.
	 */
	public HBox getContainer() {
		return container;
	}
}
