/**
 * Represents the "victory" image displayed when the player wins the game.
 *
 * The WinImage class encapsulates the setup and rendering of the victory screen.
 */
package com.example.demo.displays;

import com.example.demo.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * WinImage displays the "victory" image with dimensions matching the screen size.
 */
public class WinImage extends ImageView {

	/** The file path to the "victory" image. */
	private static final String IMAGE_NAME = Config.WIN_IMAGE;

	/** The height of the "victory" image, matching the screen height. */
	private static final int HEIGHT = Config.getScreenHeight();

	/** The width of the "victory" image, matching the screen width. */
	private static final int WIDTH = Config.getScreenWidth();

	/**
	 * Constructs a WinImage instance, initializing it with the appropriate image and dimensions.
	 */
	public WinImage() {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
	}
}
