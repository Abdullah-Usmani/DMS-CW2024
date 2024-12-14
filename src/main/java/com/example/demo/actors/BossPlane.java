/**
 * Represents the boss plane in the game.
 * Includes functionalities for movement, shield activation, firing projectiles, and taking damage.
 */
package com.example.demo.actors;

import com.example.demo.Config;
import com.example.demo.displays.ShieldDisplay;
import com.example.demo.managers.AudioManager;

import java.util.*;

public class BossPlane extends FighterPlane {

	private static final String IMAGE_NAME = Config.BOSS_IMAGE;
	private static final int IMAGE_HEIGHT = Config.BOSS_IMAGE_HEIGHT;
	private static final int IMAGE_WIDTH = Config.BOSS_IMAGE_WIDTH;   
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

	/**
	 * Constructs a BossPlane instance with its shield display and initializes movement patterns.
	 */
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

	/**
	 * Updates the position of the boss plane and its shield.
	 * Ensures the plane stays within bounds.
	 */
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

	/**
	 * Updates the boss plane's position and manages shield activation and deactivation.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Retrieves the next movement value from the boss's move pattern.
	 *
	 * @return the next move value.
	 */
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

	/**
	 * Initializes the movement pattern for the boss plane.
	 * Alternates between vertical velocities and stationary moves.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(0);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield's position to follow the boss plane.
	 */
	private void updateShieldPosition() {
		double bossCenterX = getLayoutX() + getTranslateX() + getFitWidth() / 2;
		double bossCenterY = getLayoutY() + getTranslateY() + getFitHeight() / 2;
		shieldDisplay.updatePosition(bossCenterX, bossCenterY);
	}

	/**
	 * Gets the shield display associated with the boss plane.
	 *
	 * @return the ShieldDisplay instance.
	 */
	public ShieldDisplay getShieldDisplay() {
		return shieldDisplay;
	}

	/**
	 * Fires a projectile from the boss plane if the firing condition is met.
	 *
	 * @return an instance of BossProjectile, or null if the boss does not fire in the current frame.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (bossFiresInCurrentFrame()) {
			playFiringAudio(Config.ENEMY_MISSILE_AUDIO);
			return new BossProjectile(getProjectileXPosition(), getProjectileYPosition());
		}
		return null;
	}

	/**
	 * Determines if the boss plane should take damage. Returns false if the shield is active.
	 *
	 * @return true if the boss can take damage, false otherwise.
	 */
	@Override
	protected boolean shouldTakeDamage() {
		return !isShielded;
	}

	/**
	 * Determines whether the boss fires a projectile in the current frame.
	 *
	 * @return true if the boss fires, false otherwise.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Determines whether the shield should be activated based on probability.
	 *
	 * @return true if the shield should be activated, false otherwise.
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Checks if the shield has been active for the maximum allowed frames.
	 *
	 * @return true if the shield duration is exhausted, false otherwise.
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the boss's shield.
	 */
    void activateShield() {
		isShielded = true;
	}

	/**
	 * Deactivates the boss's shield and resets shield activation frames.
	 */
    void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	/**
	 * Manages the state of the boss's shield, including activation and deactivation.
	 */
    void updateShield() {
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
}
