/**
 * Represents an abstract base class for all types of fighter planes in the game.
 * Handles shared functionalities such as firing projectiles, managing dimensions, and determining projectile positions.
 */
package com.example.demo.actors;

import com.example.demo.managers.AudioManager;

public abstract class FighterPlane extends ActiveActorDestructible {

	private final int imageHeight;
	private final int imageWidth;

	/**
	 * Constructs a FighterPlane instance with the specified attributes.
	 *
	 * @param imageName    the name of the image file representing the plane.
	 * @param imageHeight  the height of the plane's image.
	 * @param imageWidth   the width of the plane's image.
	 * @param initialXPos  the initial X position of the plane.
	 * @param initialYPos  the initial Y position of the plane.
	 * @param health       the health of the plane.
	 */
	protected FighterPlane(String imageName, int imageHeight, int imageWidth, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, imageWidth, initialXPos, initialYPos, health);
		this.imageHeight = imageHeight;
		this.imageWidth = imageWidth;
	}

	/**
	 * Abstract method to fire a projectile. Must be implemented by subclasses.
	 *
	 * @return an instance of ActiveActorDestructible representing the fired projectile.
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Plays the audio for firing a projectile.
	 *
	 * @param audioFilePath the file path of the audio to play.
	 */
	protected void playFiringAudio(String audioFilePath) {
		AudioManager.playAudio(audioFilePath);
	}

	/**
	 * Calculates the X position for a projectile fired by the plane.
	 *
	 * @return the calculated X position for the projectile.
	 */
	protected double getProjectileXPosition() {
		return getLayoutX() + getTranslateX() + getProjectileXOffset();
	}

	/**
	 * Calculates the Y position for a projectile fired by the plane.
	 *
	 * @return the calculated Y position for the projectile.
	 */
	protected double getProjectileYPosition() {
		return getLayoutY() + getTranslateY() + (imageHeight / 2.0);
	}

	/**
	 * Determines the horizontal offset for the projectile based on the type of plane.
	 *
	 * @return the X offset for the projectile.
	 */
	protected double getProjectileXOffset() {
		if (this instanceof UserPlane) {
			return imageWidth; // Positive offset for user planes
		}
		if (this instanceof BossPlane) {
			return -(0.5 * imageWidth); // Negative offset for boss planes
		}
		return -imageWidth; // Negative offset for enemy planes
	}
}