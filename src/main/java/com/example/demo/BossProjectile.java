package com.example.demo;

import com.example.demo.controller.Main;
import javafx.scene.image.Image;

public class BossProjectile extends Projectile {

	private static final int SCREEN_HEIGHT = Main.getScreenHeight();
	private static final int SCREEN_WIDTH = Main.getScreenWidth();
	private static final String IMAGE_NAME = "bossfire.png";
	private static final Image PLANE_IMAGE = new Image(UserPlane.class.getResource(IMAGE_LOCATION+IMAGE_NAME).toExternalForm());
	private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * .05);
	private static final int src_IMAGE_HEIGHT = (int) PLANE_IMAGE.getHeight(); // Dynamically get height
	private static final int src_IMAGE_WIDTH = (int) PLANE_IMAGE.getWidth();  // Dynamically get width
	private static final int IMAGE_WIDTH = (int) (SCREEN_WIDTH * .05);  // Dynamically get width
	private static final double HORIZONTAL_VELOCITY = -(SCREEN_WIDTH * .015);
	private static final int DAMAGE_MULTIPLIER = 3;

	public BossProjectile(double initialXPos, double initialYPos) {
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
