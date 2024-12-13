/**
 * Represents the "game over" image displayed when the player loses the game.
 *
 * The LoseImage class encapsulates the setup and rendering of the game-over screen.
 */
package com.example.demo.displays;

import com.example.demo.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * LoseImage displays the "game over" image with dimensions matching the screen size.
 */
public class LoseImage extends ImageView {

	/** The file path to the "game over" image. */
	private static final String IMAGE_NAME = Config.LOSE_IMAGE;

	/** The height of the "game over" image, matching the screen height. */
	private static final int HEIGHT = Config.getScreenHeight();

	/** The width of the "game over" image, matching the screen width. */
	private static final int WIDTH = Config.getScreenWidth();

	/**
	 * Constructs a LoseImage instance, initializing it with the appropriate image and dimensions.
	 */
	public LoseImage() {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
	}
}
