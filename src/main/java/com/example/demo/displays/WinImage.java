package com.example.demo.displays;

import com.example.demo.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WinImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/you-win.jpg";
	private static final int HEIGHT = Config.getScreenHeight(); // Use centralized height
	private static final int WIDTH = Config.getScreenWidth();  // Use centralized width
	
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}
}
