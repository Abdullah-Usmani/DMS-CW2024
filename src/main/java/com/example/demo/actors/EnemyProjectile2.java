package com.example.demo.actors;

import com.example.demo.Config;
import javafx.scene.image.Image;

public class EnemyProjectile2 extends Projectile {
	private static final int SCREEN_HEIGHT = Config.getScreenHeight();
	private static final int SCREEN_WIDTH = Config.getScreenWidth();
	private static final String IMAGE_NAME = "enemymissiler33.png";

	private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * .03);

	private static final int IMAGE_WIDTH = (int) (SCREEN_WIDTH * .03);   // Dynamically get width
	private static final double HORIZONTAL_VELOCITY = -(SCREEN_WIDTH * .015);
	private static final int DAMAGE_MULTIPLIER = 3;

	public EnemyProjectile2(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, IMAGE_WIDTH, initialXPos, initialYPos, HORIZONTAL_VELOCITY, DAMAGE_MULTIPLIER);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		super.updatePosition();
	}

	@Override
	public void updateActor() {
		updatePosition();
	}
}
