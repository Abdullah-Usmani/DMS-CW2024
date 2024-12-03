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
//			System.out.println("UserPlane x offset:" + imageWidth);
			return (2*imageWidth); // Positive offset for user
		}
		if (this instanceof BossPlane) {
//			System.out.println("BossPlane x offset:" + imageWidth);
			return -(2*imageWidth); // Negative
		}
		else {
//			System.out.println("EnemyPlane x offset:" + imageWidth);
			return -(2*imageWidth); // Negative offset for enemy
		}
	}
}