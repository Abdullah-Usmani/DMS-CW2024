/**
 * Represents the third type of enemy plane in the game.
 * Handles movement and firing mechanisms, including shooting advanced projectiles.
 */
package com.example.demo.actors;

import com.example.demo.Config;
import com.example.demo.managers.AudioManager;

public class EnemyPlane3 extends FighterPlane {

	private static final int SCREEN_HEIGHT = Config.getScreenHeight();
	private static final int SCREEN_WIDTH = Config.getScreenWidth();
	private static final String IMAGE_NAME = Config.ENEMY3_IMAGE;
	private static final double SCALAR = Config.ENEMY3_SCALAR;
	private static final int IMAGE_HEIGHT = (int) (SCREEN_HEIGHT * SCALAR);
	private static final int IMAGE_WIDTH = (int) (SCREEN_WIDTH * SCALAR);  
	private static final int HORIZONTAL_VELOCITY = Config.ENEMY3_HORIZONTAL_VELOCITY;
	private static final int INITIAL_HEALTH = Config.ENEMY3_INITIAL_HEALTH;
	private static final double FIRE_RATE = Config.ENEMY3_FIRE_RATE;

	/**
	 * Constructs an EnemyPlane3 instance with the specified initial position.
	 *
	 * @param initialXPos the initial X position of the plane.
	 * @param initialYPos the initial Y position of the plane.
	 */
	public EnemyPlane3(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, IMAGE_WIDTH, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		super.updatePosition();
	}

	/**
	 * Fires an advanced projectile from the enemy plane based on the fire rate.
	 *
	 * @return an instance of EnemyProjectile2, or null if the fire rate condition is not met.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			playFiringAudio(Config.ENEMY_MISSILE_AUDIO); // Play enemy-specific Audio
			return new EnemyProjectile2(getProjectileXPosition(), getProjectileYPosition());
		}
		return null;
	}

	/**
	 * Updates the actor by updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}