package com.example.demo;

import com.example.demo.controller.Main;

public class BossProjectile extends Projectile {

	private static final int SCREEN_HEIGHT = Main.getScreenHeight();
	private static final int SCREEN_WIDTH = Main.getScreenWidth();
	private static final String IMAGE_NAME = "bossfire.png";
	private static final int IMAGE_HEIGHT = (int) (SCREEN_HEIGHT * .05);
	private static final int HORIZONTAL_VELOCITY = (int) -(SCREEN_WIDTH * .02);
	private static final int INITIAL_X_POSITION = (int) Boss.getBossXPosition();

	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
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
