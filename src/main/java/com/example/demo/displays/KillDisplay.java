package com.example.demo.displays;

import com.example.demo.Config;
import com.example.demo.managers.StyleManager;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class KillDisplay {

	private static final String KILL_IMAGE_NAME = Config.KILL_IMAGE;
	private static final double KILL_HEIGHT = Config.KILL_SIZE;

	private HBox container;
	private final double containerXPosition;
	private final double containerYPosition;
	private Label killCounter; // To display the kill count
	private final int killsNeeded; // Tracks the current kill count

	public KillDisplay(double xPosition, double yPosition, int killsNeeded) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.killsNeeded = killsNeeded;
		initializeContainer();
		initializeKills();
	}

	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
		container.setSpacing(10); // Adds some spacing between elements
		container.setAlignment(Pos.CENTER_LEFT); // Align items vertically
	}

	private void initializeKills() {
		// Kill Image
		ImageView kill = new ImageView(new Image(getClass().getResource(KILL_IMAGE_NAME).toExternalForm()));
		kill.setFitHeight(KILL_HEIGHT);
		kill.setPreserveRatio(true);

		// Kill Counter Label
		killCounter = StyleManager.createStyledLabel("" + 0, true, 0.025); // Initial kill count

		// Ensure the label is vertically aligned with the image
		killCounter.setStyle("-fx-alignment: center-left;");

		// Add the image and label to the container
		container.getChildren().addAll(kill, killCounter);
	}

	public void updateKills(int killCount) {
		killCounter.setText(+killCount + " / " + killsNeeded); // Update the counter label
	}

	public HBox getContainer() {
		return container;
	}
}
