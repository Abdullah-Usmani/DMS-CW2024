package com.example.demo;

import com.example.demo.controller.Main;
import javafx.scene.image.Image;

import java.util.*;

public class BossPlane extends FighterPlane {

	private static final int SCREEN_HEIGHT = Main.getScreenHeight();
	private static final int SCREEN_WIDTH = Main.getScreenWidth();
	private static final String IMAGE_NAME = "c17.png";
	private static final Image PLANE_IMAGE = new Image(UserPlane.class.getResource("/com/example/demo/images/"+IMAGE_NAME).toExternalForm());
	private static final int IMAGE_HEIGHT = (int) PLANE_IMAGE.getHeight(); // Dynamically get height
	private static final int IMAGE_WIDTH = (int) PLANE_IMAGE.getWidth();  // Dynamically get width
	private static final double INITIAL_X_POSITION = SCREEN_WIDTH - (SCREEN_WIDTH * .3);
	private static final double INITIAL_Y_POSITION = SCREEN_HEIGHT * .5;
	private static final int Y_POSITION_UPPER_BOUND =  (int) -(SCREEN_HEIGHT * .01);
	private static final int Y_POSITION_LOWER_BOUND = SCREEN_HEIGHT - (2*IMAGE_HEIGHT) - Y_POSITION_UPPER_BOUND;
	private static final int VERTICAL_VELOCITY = (int) (SCREEN_HEIGHT * .0075);
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = .01;
	private static final int HEALTH = 15;
	private static final int ZERO = 0;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int MAX_FRAMES_WITH_SHIELD = 50;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;
	private LevelView levelView;


	public BossPlane() {
		super(IMAGE_NAME, IMAGE_HEIGHT, IMAGE_WIDTH, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
		super.updatePosition();
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileXPosition(), getProjectileYPosition()) : null;
	}

	@Override
	protected boolean shouldTakeDamage() {
		return !isShielded;
	}

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	private void activateShield() {
		isShielded = true;
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
		} else if (shieldShouldBeActivated()) {
			activateShield();
			levelView.showShield();
		}
		if (shieldExhausted()) {
			deactivateShield();
			levelView.hideShield();
		}
	}

	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	public void setLevelView(LevelView levelView) {
		this.levelView = levelView;
	}
}
