package com.example.demo.actors;

import com.example.demo.Config;
import javafx.scene.image.Image;

public class EnemyPlane3 extends FighterPlane {

	private static final int SCREEN_HEIGHT = Config.getScreenHeight();
	private static final int SCREEN_WIDTH = Config.getScreenWidth();
	private static final String IMAGE_NAME = "enemya10c.png";

	private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * .08);

	private static final int IMAGE_WIDTH =  (int) (SCREEN_WIDTH * .08);   // Dynamically get width
	private static final int HORIZONTAL_VELOCITY = (int) -(SCREEN_WIDTH * .0025);;
	private static final int INITIAL_HEALTH = 3;
	private static final double FIRE_RATE = .02;

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
