package com.example.demo.actors;

import com.example.demo.Config;
import com.example.demo.displays.ShieldDisplay;
import com.example.demo.levels.LevelView;

import java.util.*;

public class BossPlane extends FighterPlane {

	private static final int SCREEN_HEIGHT = Config.getScreenHeight();
	private static final int SCREEN_WIDTH = Config.getScreenWidth();
	private static final String IMAGE_NAME = Config.BOSS_IMAGE;
	private static final int IMAGE_HEIGHT =  Config.BOSS_IMAGE_HEIGHT;
	private static final int IMAGE_WIDTH =  Config.BOSS_IMAGE_WIDTH;   // Dynamically get width
	private static final double INITIAL_X_POSITION = Config.BOSS_INITIAL_X_POSITION;
	private static final double INITIAL_Y_POSITION = Config.BOSS_INITIAL_Y_POSITION;
	private static final int Y_POSITION_UPPER_BOUND = Config.BOSS_Y_POSITION_UPPER_BOUND;
	private static final int Y_POSITION_LOWER_BOUND = Config.BOSS_Y_POSITION_LOWER_BOUND;
	private static final int VERTICAL_VELOCITY = Config.BOSS_VERTICAL_VELOCITY;
	private static final double BOSS_FIRE_RATE = Config.BOSS_FIRE_RATE;
	private static final double BOSS_SHIELD_PROBABILITY = Config.BOSS_SHIELD_PROBABILITY;
	private static final int HEALTH = Config.BOSS_HEALTH;
	private static final int MOVE_FREQUENCY_PER_CYCLE = Config.MOVE_FREQUENCY_PER_CYCLE;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = Config.MAX_FRAMES_WITH_SAME_MOVE;
	private static final int MAX_FRAMES_WITH_SHIELD = Config.MAX_FRAMES_WITH_SHIELD;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;
	private final ShieldDisplay shieldDisplay;


	public BossPlane() {
		super(IMAGE_NAME, IMAGE_HEIGHT, IMAGE_WIDTH, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		this.shieldDisplay = new ShieldDisplay(INITIAL_X_POSITION, INITIAL_Y_POSITION);
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
		updateShieldPosition();
	}

	private void updateShieldPosition() {
		double bossCenterX = getLayoutX() + getTranslateX() + getFitWidth() / 2;
		double bossCenterY = getLayoutY() + getTranslateY() + getFitHeight() / 2;
		shieldDisplay.updatePosition(bossCenterX, bossCenterY);
	}

	public ShieldDisplay getShieldDisplay() {
		return shieldDisplay;
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (bossFiresInCurrentFrame()) {
			playFiringAudio(Config.ENEMY_MISSILE_AUDIO); // Play enemy-specific Audio
			return new BossProjectile(getProjectileXPosition(), getProjectileYPosition());
		}
		else {
			return null;
		}
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
			shieldDisplay.showShield();
		}
		if (shieldExhausted()) {
			deactivateShield();
			shieldDisplay.hideShield();
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
			movePattern.add(0);
		}
		Collections.shuffle(movePattern);
	}
}
