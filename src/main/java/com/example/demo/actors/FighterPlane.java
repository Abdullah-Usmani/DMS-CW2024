package com.example.demo.actors;

import javafx.scene.media.AudioClip;

public abstract class FighterPlane extends ActiveActorDestructible {

	private final int imageHeight;
	private final int imageWidth;

    public FighterPlane(String imageName, int imageHeight, int imageWidth, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, imageWidth, initialXPos, initialYPos, health);
		this.imageHeight = imageHeight;
		this.imageWidth = imageWidth;
    }

	public abstract ActiveActorDestructible fireProjectile();

	// Plays the firing sound for the plane
	protected void playFiringSound(String audioFilePath) {
		AudioClip fireSound = new AudioClip(getClass().getResource(audioFilePath).toExternalForm());
		fireSound.play();
	}

	protected double getProjectileXPosition() {
		return getLayoutX() + getTranslateX() + getProjectileXOffset();
	}

	protected double getProjectileYPosition() {
		return getLayoutY() + getTranslateY() + (imageHeight / 2.0);
	}

	// Method to determine X offset dynamically
	protected double getProjectileXOffset() {
		if (this instanceof UserPlane) {
			return (imageWidth); // Positive offset for user
		}
		if (this instanceof BossPlane) {
			return -(.5*imageWidth); // Negative
		}
		else {
			return -(imageWidth); // Negative offset for enemy
		}
	}
}