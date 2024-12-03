package com.example.demo;

import com.example.demo.controller.Main;

public abstract class FighterPlane extends ActiveActorDestructible {

	private final int imageHeight;
	private final int imageWidth;

    public FighterPlane(String imageName, int imageHeight, int imageWidth, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, imageWidth, initialXPos, initialYPos, health);
		this.imageHeight = imageHeight;
		this.imageWidth = imageWidth;
    }

	public abstract ActiveActorDestructible fireProjectile();

	protected double getProjectileXPosition() {
		return getLayoutX() + getTranslateX() + getProjectileXOffset();
	}

	protected double getProjectileYPosition() {
		return getLayoutY() + getTranslateY() + (imageHeight / 2.0);
	}

	// Method to determine X offset dynamically
	protected double getProjectileXOffset() {
		if (this instanceof UserPlane) {
			return (imageWidth * .1); // Positive offset for user
		}
		if (this instanceof BossPlane) {
			return -(imageWidth * .2); // Negative
		}
		else {
			return -(imageWidth * .1); // Negative offset for enemy
		}
	}
}