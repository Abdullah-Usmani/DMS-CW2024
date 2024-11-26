package com.example.demo;

import com.example.demo.controller.Main;

public class UserProjectile extends Projectile {
	private static final int SCREEN_HEIGHT = Main.getScreenHeight();
	private static final int SCREEN_WIDTH = Main.getScreenWidth();
	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * .01);
	private static final int HORIZONTAL_VELOCITY = (int) (SCREEN_WIDTH * .01);

	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
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
