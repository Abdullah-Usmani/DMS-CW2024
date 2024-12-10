package com.example.demo.actors;

import com.example.demo.Config;
import javafx.scene.image.Image;

public class EnemyPlane extends FighterPlane {

	private static final int SCREEN_HEIGHT = Config.getScreenHeight();
	private static final int SCREEN_WIDTH = Config.getScreenWidth();
	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * .05);
	private static final int IMAGE_WIDTH =  (int) (SCREEN_WIDTH * .05);  // Dynamically get width
	private static final int HORIZONTAL_VELOCITY = (int) -(SCREEN_WIDTH * .0025);;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, IMAGE_WIDTH, initialXPos, initialYPos, INITIAL_HEALTH);
		System.out.println("initial X: " + initialXPos + "initial Y: " + initialYPos);
		System.out.println("Horizontal Velocity " + HORIZONTAL_VELOCITY);
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
