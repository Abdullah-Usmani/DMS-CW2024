/**
 * Represents the player's plane in the game, capable of firing projectiles and missiles.
 * Manages movement, firing mechanisms, and tracking statistics such as hits and kills.
 */
package com.example.demo.actors;

import com.example.demo.Config;
import com.example.demo.managers.AudioManager;

public class UserPlane extends FighterPlane {

	private static final int SCREEN_HEIGHT = Config.getScreenHeight();
	private static final int SCREEN_WIDTH = Config.getScreenWidth();
	private static final String IMAGE_NAME = Config.USER_IMAGE;
	private static final double SCALAR = Config.USER_SCALAR;
	private static final int IMAGE_HEIGHT = (int) (SCREEN_HEIGHT * SCALAR);
	private static final int IMAGE_WIDTH = (int) (SCREEN_WIDTH * SCALAR);  
	private static final double INITIAL_X_POSITION = SCREEN_WIDTH * Config.USER_INITIAL_X_POSITION_SCALAR;
	private static final double INITIAL_Y_POSITION = SCREEN_HEIGHT * Config.USER_INITIAL_Y_POSITION_SCALAR;
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = (double) SCREEN_HEIGHT - 2 * IMAGE_HEIGHT;
	private static final double VERTICAL_VELOCITY = Config.USER_VERTICAL_VELOCITY;
	private static final long GUN_COOLDOWN = Config.USER_GUN_COOLDOWN; // Cooldown in milliseconds
	private static final long MISSILE_COOLDOWN = Config.USER_MISSILE_COOLDOWN; // Cooldown in milliseconds
	private int velocityMultiplier;
	private int numberOfHits;
	private int numberOfKills;
	private long lastFiredTime; // Tracks the last projectile fire time

	/**
	 * Constructs a UserPlane instance with the specified initial health.
	 *
	 * @param initialHealth the initial health of the plane.
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, IMAGE_WIDTH, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
	}

	/**
	 * Updates the position of the plane based on the current velocity multiplier,
	 * ensuring it stays within screen bounds.
	 */
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
			super.updatePosition();
		}
	}

	/**
	 * Updates the actor by updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the plane if the gun cooldown period has elapsed.
	 *
	 * @return an instance of UserProjectile, or null if the gun is on cooldown.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFiredTime >= GUN_COOLDOWN) { // Check cooldown
			lastFiredTime = currentTime; // Update last fired time
			AudioManager.playAudio(Config.FRIENDLY_GUN_AUDIO); // Play user-specific Audio
			return new UserProjectile(getProjectileXPosition(), getProjectileYPosition());
		} else {
			return null;
		}
	}

	/**
	 * Fires a missile from the plane if the missile cooldown period has elapsed.
	 *
	 * @return an instance of UserMissile, or null if the missile is on cooldown.
	 */
	public ActiveActorDestructible fireMissile() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFiredTime >= MISSILE_COOLDOWN) { // Check cooldown
			lastFiredTime = currentTime; // Update last fired time
			AudioManager.playAudio(Config.FRIENDLY_MISSILE_AUDIO); // Play enemy-specific Audio
			return new UserMissile(getProjectileXPosition(), getProjectileYPosition());
		} else {
			return null;
		}
	}

	/**
	 * Checks if the plane is currently moving.
	 *
	 * @return true if the plane is moving, false otherwise.
	 */
	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	/**
	 * Starts upward movement for the plane.
	 */
	public void moveUp() {
		velocityMultiplier = -1;
	}

	/**
	 * Starts downward movement for the plane.
	 */
	public void moveDown() {
		velocityMultiplier = 1;
	}

	/**
	 * Stops all movement of the plane.
	 */
	public void stop() {
		velocityMultiplier = 0;
	}

	/**
	 * Gets the number of hits registered by the plane.
	 *
	 * @return the number of hits.
	 */
	public int getNumberOfHits() {
		return numberOfHits;
	}

	/**
	 * Increments the hit count for the plane.
	 */
	public void incrementHitCount() {
		numberOfHits++;
	}

	/**
	 * Gets the number of kills registered by the plane.
	 *
	 * @return the number of kills.
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the kill count for the plane.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}
}
