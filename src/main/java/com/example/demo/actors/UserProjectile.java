package com.example.demo.actors;

import com.example.demo.Config;
import javafx.scene.image.Image;

public class UserProjectile extends Projectile {
	private static final int SCREEN_HEIGHT = Config.getScreenHeight();
	private static final int SCREEN_WIDTH = Config.getScreenWidth();
	private static final String IMAGE_NAME = Config.FRIENDLY_GUN;
	private static final double SCALAR =  Config.USER_PROJECTILE_SCALAR;
	private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * SCALAR);
	private static final int IMAGE_WIDTH =  (int) (SCREEN_WIDTH * SCALAR);  // Dynamically get width
	private static final int HORIZONTAL_VELOCITY = (int) Config.USER_PROJECTILE_HORIZONTAL_VELOCITY;
	private static final int DAMAGE = Config.USER_PROJECTILE_DAMAGE;

	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, IMAGE_WIDTH, initialXPos, initialYPos, HORIZONTAL_VELOCITY, DAMAGE);
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
