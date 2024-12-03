package com.example.demo;

import com.example.demo.controller.Main;
import javafx.scene.image.Image;

public class EnemyPlane3 extends FighterPlane {

	private static final int SCREEN_HEIGHT = Main.getScreenHeight();
	private static final int SCREEN_WIDTH = Main.getScreenWidth();
	private static final String IMAGE_NAME = "a10c.png";
	private static final Image PLANE_IMAGE = new Image(UserPlane.class.getResource("/com/example/demo/images/"+IMAGE_NAME).toExternalForm());
	private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * .075);
	private static final int src_IMAGE_HEIGHT = (int) PLANE_IMAGE.getHeight(); // Dynamically get height
	private static final int src_IMAGE_WIDTH = (int) PLANE_IMAGE.getWidth();  // Dynamically get width
	private static final int IMAGE_WIDTH = (src_IMAGE_WIDTH * IMAGE_HEIGHT) / src_IMAGE_HEIGHT;  // Dynamically get width
	private static final int HORIZONTAL_VELOCITY = (int) -(SCREEN_WIDTH * .0025);;
	private static final int INITIAL_HEALTH = 3;
	private static final double FIRE_RATE = .02;

	public EnemyPlane3(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, IMAGE_WIDTH, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		super.updatePosition();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition();
			double projectileYPosition = getProjectileYPosition();
			return new EnemyProjectile2(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

}
