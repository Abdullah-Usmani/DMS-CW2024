/**
 * Represents a projectile fired by the boss plane in the game.
 * Handles movement, damage, and image properties specific to boss projectiles.
 */
package com.example.demo.actors;

import com.example.demo.Config;

public class BossProjectile extends Projectile {

	private static final int SCREEN_HEIGHT = Config.getScreenHeight();
	private static final int SCREEN_WIDTH = Config.getScreenWidth();
	private static final String IMAGE_NAME = Config.BOSS_MISSILE;
	private static final int IMAGE_HEIGHT = (int) (SCREEN_HEIGHT * 0.05);
	private static final int IMAGE_WIDTH = (int) (SCREEN_WIDTH * 0.05);  
	private static final double HORIZONTAL_VELOCITY = -(SCREEN_WIDTH * 0.015);
	private static final int DAMAGE_MULTIPLIER = 3;

	/**
	 * Constructs a BossProjectile instance with the specified initial position.
	 *
	 * @param initialXPos the initial X position of the projectile.
	 * @param initialYPos the initial Y position of the projectile.
	 */
	public BossProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, IMAGE_WIDTH, initialXPos, initialYPos, HORIZONTAL_VELOCITY, DAMAGE_MULTIPLIER);
	}

	/**
	 * Updates the position of the boss projectile by moving it horizontally.
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
