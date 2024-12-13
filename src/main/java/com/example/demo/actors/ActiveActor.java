/**
 * Represents an active actor in the game.
 * This is an abstract class for all moving entities with image-based visual representation.
 */
package com.example.demo.actors;

import javafx.scene.image.*;

public abstract class ActiveActor extends ImageView {

	/**
	 * Constructs an ActiveActor instance with the specified image and initial properties.
	 *
	 * @param imageName    the name of the image file representing the actor.
	 * @param imageHeight  the height of the actor's image.
	 * @param imageWidth   the width of the actor's image.
	 * @param initialXPos  the initial X position of the actor.
	 * @param initialYPos  the initial Y position of the actor.
	 */
	protected ActiveActor(String imageName, int imageHeight, int imageWidth, double initialXPos, double initialYPos) {
		this.setImage(new Image(getClass().getResource(imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Updates the position of the actor. Must be implemented by subclasses.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by a specified amount.
	 *
	 * @param horizontalMove the distance to move horizontally.
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by a specified amount.
	 *
	 * @param verticalMove the distance to move vertically.
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}
}