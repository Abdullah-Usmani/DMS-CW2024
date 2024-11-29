package com.example.demo;

import com.example.demo.controller.Main;

public class UserPlane extends FighterPlane {

	private static final int SCREEN_HEIGHT = Main.getScreenHeight();
	private static final int SCREEN_WIDTH = Main.getScreenWidth();
	private static final String IMAGE_NAME = "userplane1.png";
	private static final int IMAGE_HEIGHT = (int) (SCREEN_HEIGHT * .05);
	private static final double INITIAL_X_POSITION = SCREEN_WIDTH * .01;
	private static final double INITIAL_Y_POSITION = SCREEN_HEIGHT * .5;
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = (double) SCREEN_HEIGHT - 2*IMAGE_HEIGHT;
	private static final int VERTICAL_VELOCITY = (int) (SCREEN_HEIGHT * .015);
	private static final int PROJECTILE_X_POSITION = (int) (SCREEN_WIDTH * .05);
	private static final int PROJECTILE_Y_POSITION_OFFSET = IMAGE_HEIGHT/2;
	private static final long FIRE_RATE_COOLDOWN = 150; // Cooldown in milliseconds
	private int velocityMultiplier;
	private int numberOfHits;
	private long lastFiredTime; // Tracks the last projectile fire time

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
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
		if (currentTime - lastFiredTime >= FIRE_RATE_COOLDOWN) { // Check cooldown
			lastFiredTime = currentTime; // Update last fired time
			return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
		} else {
			// Cooldown active, no projectile fired
//			System.out.println("Cooldown active, cannot fire yet!");
			return null;
		}
	}

	public ActiveActorDestructible fireMissile() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFiredTime >= FIRE_RATE_COOLDOWN) { // Check cooldown
			lastFiredTime = currentTime; // Update last fired time
			return new UserMissile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
		} else {
			// Cooldown active, no projectile fired
//			System.out.println("Cooldown active, cannot fire yet!");
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

//	public void incrementKillCount() {
//		numberOfKills++;
//	}
}
