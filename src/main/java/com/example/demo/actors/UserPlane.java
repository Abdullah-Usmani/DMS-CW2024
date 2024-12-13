package com.example.demo.actors;

import com.example.demo.Config;
import com.example.demo.managers.AudioManager;

public class UserPlane extends FighterPlane {

	private static final int SCREEN_HEIGHT = Config.getScreenHeight();
	private static final int SCREEN_WIDTH = Config.getScreenWidth();
	private static final String IMAGE_NAME = Config.userImage;
	private static final double SCALAR = Config.userScalar;
	private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * SCALAR);
	private static final int IMAGE_WIDTH =  (int) (SCREEN_WIDTH * SCALAR);  // Dynamically get width
	private static final double INITIAL_X_POSITION = SCREEN_WIDTH * Config.userInitialXPositionScalar;
	private static final double INITIAL_Y_POSITION = SCREEN_HEIGHT * Config.userInitialYPositionScalar;
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = (double) SCREEN_HEIGHT - 2*IMAGE_HEIGHT;
	private static final double VERTICAL_VELOCITY = Config.userVerticalVelocity;
	private static final long GUN_COOLDOWN = Config.userGunCooldown; // Cooldown in milliseconds
	private static final long MISSILE_COOLDOWN = Config.userMissileCooldown; // Cooldown in milliseconds
	private int velocityMultiplier;
	private int numberOfHits;
	private int numberOfKills;
	private long lastFiredTime; // Tracks the last projectile fire time

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, IMAGE_WIDTH, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
	}
	
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
	
	@Override
	public void updateActor() {
		updatePosition();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFiredTime >= GUN_COOLDOWN) { // Check cooldown
			lastFiredTime = currentTime; // Update last fired time
			AudioManager.playAudio(Config.friendlyGunAudio); // Play user-specific Audio
			return new UserProjectile(getProjectileXPosition(), getProjectileYPosition());
		} else {
			return null;
		}
	}

	public ActiveActorDestructible fireMissile() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFiredTime >= MISSILE_COOLDOWN) { // Check cooldown
			lastFiredTime = currentTime; // Update last fired time
			AudioManager.playAudio(Config.friendlyMissileAudio); // Play enemy-specific Audio
			return new UserMissile(getProjectileXPosition(), getProjectileYPosition());
		} else {
			return null;
		}
	}

	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	public void moveUp() {
		velocityMultiplier = -1;
	}

	public void moveDown() {
		velocityMultiplier = 1;
	}

	public void stop() {
		velocityMultiplier = 0;
	}

	public int getNumberOfHits() {
		return numberOfHits;
	}

	public void incrementHitCount() {
		numberOfHits++;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}
}
