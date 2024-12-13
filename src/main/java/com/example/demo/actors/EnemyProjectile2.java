/**
 * Represents an advanced projectile fired by enemy planes in the game.
 * Handles movement, damage, and image properties specific to advanced enemy projectiles.
 */
package com.example.demo.actors;

import com.example.demo.Config;

public class EnemyProjectile2 extends Projectile {

	private static final int SCREEN_HEIGHT = Config.getScreenHeight();
	private static final int SCREEN_WIDTH = Config.getScreenWidth();
	private static final String IMAGE_NAME = Config.ENEMY_MISSILE;
	private static final double SCALAR = Config.ENEMY_MISSILE_SCALAR;
	private static final int IMAGE_HEIGHT = (int) (SCREEN_HEIGHT * SCALAR);
	private static final int IMAGE_WIDTH = (int) (SCREEN_WIDTH * SCALAR);  
	private static final int HORIZONTAL_VELOCITY = (int) Config.ENEMY_MISSILE_HORIZONTAL_VELOCITY;
	private static final int DAMAGE = Config.ENEMY_MISSILE_DAMAGE;

	/**
	 * Constructs an EnemyProjectile2 instance with the specified initial position.
	 *
	 * @param initialXPos the initial X position of the projectile.
	 * @param initialYPos the initial Y position of the projectile.
	 */
	public EnemyProjectile2(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, IMAGE_WIDTH, initialXPos, initialYPos, HORIZONTAL_VELOCITY, DAMAGE);
	}

	/**
	 * Updates the position of the advanced enemy projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		super.updatePosition();
	}

	/**
	 * Updates the actor by updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}
