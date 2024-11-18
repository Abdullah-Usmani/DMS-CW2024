package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class KillDisplay {

	private static final String KILL_IMAGE_NAME = "/com/example/demo/images/killcount.png";
	private static final int KILL_HEIGHT = 50;
	private static final int INDEX_OF_FIRST_ITEM = 0;
	private HBox container;
	private double containerXPosition;
	private double containerYPosition;
	private int numberOfKillsToDisplay;

	public KillDisplay(double xPosition, double yPosition, int killsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfKillsToDisplay = killsToDisplay;
		initializeContainer();
		initializeKills();
	}
	
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);		
	}
	
	private void initializeKills() {
		for (int i = 0; i < numberOfKillsToDisplay; i++) {
			ImageView kill = new ImageView(new Image(getClass().getResource(KILL_IMAGE_NAME).toExternalForm()));

			kill.setFitHeight(KILL_HEIGHT);
			kill.setPreserveRatio(true);
			container.getChildren().add(kill);
		}
	}
	
	public void addKills() {
		if (!container.getChildren().isEmpty())
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
	}
	
	public HBox getContainer() {
		return container;
	}

}
