package com.example.demo.actors;

import com.example.demo.Config;
import javafx.scene.image.Image;

public class EnemyPlane3 extends FighterPlane {

	private static final int SCREEN_HEIGHT = Config.getScreenHeight();
	private static final int SCREEN_WIDTH = Config.getScreenWidth();
	private static final String IMAGE_NAME = Config.ENEMY3_IMAGE;
	private static final double SCALAR =  Config.ENEMY3_SCALAR;
	private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * SCALAR);
	private static final int IMAGE_WIDTH =  (int) (SCREEN_WIDTH * SCALAR);  // Dynamically get width
	private static final int HORIZONTAL_VELOCITY = Config.ENEMY3_HORIZONTAL_VELOCITY;
	private static final int INITIAL_HEALTH = Config.ENEMY3_INITIAL_HEALTH;
	private static final double FIRE_RATE = Config.ENEMY3_FIRE_RATE;

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
			playFiringSound("/com/example/demo/audio/fortnite-rpg.mp3"); // Play enemy-specific sound
			return new EnemyProjectile2(getProjectileXPosition(), getProjectileYPosition());
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

}
