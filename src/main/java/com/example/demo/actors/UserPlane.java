package com.example.demo.actors;

import com.example.demo.Config;
import com.example.demo.managers.SoundManager;
import javafx.scene.image.Image;

public class UserPlane extends FighterPlane {

	private static final int SCREEN_HEIGHT = Config.getScreenHeight();
	private static final int SCREEN_WIDTH = Config.getScreenWidth();
	private static final String IMAGE_NAME = Config.USER_IMAGE;
	private static final double SCALAR = Config.USER_SCALAR;
	private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * SCALAR);
	private static final int IMAGE_WIDTH =  (int) (SCREEN_WIDTH * SCALAR);  // Dynamically get width
	private static final double INITIAL_X_POSITION = SCREEN_WIDTH * Config.USER_INITIAL_X_POSITION_SCALAR;
	private static final double INITIAL_Y_POSITION = SCREEN_HEIGHT * Config.USER_INITIAL_Y_POSITION_SCALAR;
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = (double) SCREEN_HEIGHT - 2*IMAGE_HEIGHT;
	private static final double VERTICAL_VELOCITY = Config.USER_VERTICAL_VELOCITY;
	private static final long GUN_COOLDOWN = Config.USER_GUN_COOLDOWN; // Cooldown in milliseconds
	private static final long MISSILE_COOLDOWN = Config.USER_MISSILE_COOLDOWN; // Cooldown in milliseconds
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
			SoundManager.playSound(Config.FRIENDLY_GUN_AUDIO); // Play user-specific sound
			return new UserProjectile(getProjectileXPosition(), getProjectileYPosition());
		} else {
			return null;
		}
	}

	public ActiveActorDestructible fireMissile() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFiredTime >= MISSILE_COOLDOWN) { // Check cooldown
			lastFiredTime = currentTime; // Update last fired time
			SoundManager.playSound(Config.FRIENDLY_MISSILE_AUDIO); // Play enemy-specific sound
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
