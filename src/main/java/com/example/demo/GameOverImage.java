package com.example.demo;

import com.example.demo.controller.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";
	private static final int HEIGHT = Main.getScreenHeight(); // Use centralized height
	private static final int WIDTH = Main.getScreenWidth();  // Use centralized width

	public GameOverImage(double xPosition, double yPosition) {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}
}
