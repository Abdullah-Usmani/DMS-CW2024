package com.example.demo.displays;

import com.example.demo.Config;
import com.example.demo.managers.SoundManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoseImage extends ImageView {
	
	private static final String IMAGE_NAME = Config.LOSE_IMAGE;
	private static final int HEIGHT = Config.getScreenHeight(); // Use centralized height
	private static final int WIDTH = Config.getScreenWidth();  // Use centralized width

	public LoseImage() {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
	}
}
