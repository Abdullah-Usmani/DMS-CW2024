package com.example.demo.actors;

import com.example.demo.Config;
import com.example.demo.managers.AudioManager;

public class EnemyPlane2 extends FighterPlane {

	private static final int SCREEN_HEIGHT = Config.getScreenHeight();
	private static final int SCREEN_WIDTH = Config.getScreenWidth();
	private static final String IMAGE_NAME = Config.enemy2Image;
	private static final double SCALAR =  Config.enemy2Scalar;
	private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * SCALAR);
	private static final int IMAGE_WIDTH =  (int) (SCREEN_WIDTH * SCALAR);  // Dynamically get width
	private static final int HORIZONTAL_VELOCITY = Config.enemy2HorizontalVelocity;
	private static final int INITIAL_HEALTH = Config.enemy2InitialHealth;
	private static final double FIRE_RATE = Config.enemy2FireRate;

	public EnemyPlane2(double initialXPos, double initialYPos) {
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
			AudioManager.playAudio(Config.enemyGunAudio); // Play enemy-specific Audio
			return new EnemyProjectile(getProjectileXPosition(), getProjectileYPosition());
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

}
