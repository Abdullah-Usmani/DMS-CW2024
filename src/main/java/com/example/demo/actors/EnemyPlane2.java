package com.example.demo.actors;

import com.example.demo.Config;
import javafx.scene.image.Image;

public class EnemyPlane2 extends FighterPlane {

	private static final int SCREEN_HEIGHT = Config.getScreenHeight();
	private static final int SCREEN_WIDTH = Config.getScreenWidth();
	private static final String IMAGE_NAME = Config.ENEMY2_IMAGE;
	private static final double SCALAR =  Config.ENEMY2_SCALAR;
	private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * SCALAR);
	private static final int IMAGE_WIDTH =  (int) (SCREEN_WIDTH * SCALAR);  // Dynamically get width
	private static final int HORIZONTAL_VELOCITY = Config.ENEMY2_HORIZONTAL_VELOCITY;
	private static final int INITIAL_HEALTH = Config.ENEMY2_INITIAL_HEALTH;
	private static final double FIRE_RATE = Config.ENEMY2_FIRE_RATE;

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
			playFiringSound("/com/example/demo/audio/ricochet-1.mp3"); // Play enemy-specific sound
			return new EnemyProjectile(getProjectileXPosition(), getProjectileYPosition());
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

}
