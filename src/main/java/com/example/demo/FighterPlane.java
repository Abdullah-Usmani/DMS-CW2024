package com.example.demo;

import com.example.demo.controller.Main;

public abstract class FighterPlane extends ActiveActorDestructible {

	private static final int SCREEN_HEIGHT = Main.getScreenHeight();
	private static final int SCREEN_WIDTH = Main.getScreenWidth();
	private final int imageHeight;

    public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos, health);
		this.imageHeight = imageHeight;
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
			return (SCREEN_WIDTH * .1); // Positive offset for user
		} else {
			return -(SCREEN_WIDTH * .1); // Negative offset for enemy
		}
	}
}