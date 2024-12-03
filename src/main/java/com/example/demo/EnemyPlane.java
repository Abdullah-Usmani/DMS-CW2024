package com.example.demo;

import com.example.demo.controller.Main;

public class EnemyPlane extends FighterPlane {

	private static final int SCREEN_HEIGHT = Main.getScreenHeight();
	private static final int SCREEN_WIDTH = Main.getScreenWidth();
	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = (int) (SCREEN_HEIGHT * .05);
	private static final int HORIZONTAL_VELOCITY = (int) -(SCREEN_WIDTH * .0025);;
	private static final double PROJECTILE_X_POSITION_OFFSET = (int) -(SCREEN_WIDTH * .05);
	private static final double PROJECTILE_Y_POSITION_OFFSET = (double) IMAGE_HEIGHT /2;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		super.updatePosition();
//		System.out.println("nice");
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
//			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileXPosition = getProjectileXPosition();
//			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition();
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

}
