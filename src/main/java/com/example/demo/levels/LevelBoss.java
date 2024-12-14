/**
 * Represents the boss level in the game "F-15: Strike Eagle."
 *
 * The LevelBoss class defines the unique logic and configurations for the final boss encounter.
 */
package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.actors.BossPlane;
import com.example.demo.displays.ActorInfo;
import com.example.demo.displays.BossHeartDisplay;

import java.util.List;

/**
 * LevelBoss manages the boss encounter, including boss behavior, health display,
 * and player interaction during the final level.
 */
public class LevelBoss extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = Config.LEVELBOSS_BACKGROUND;
	private static final int PLAYER_INITIAL_HEALTH = Config.LEVEL_BOSS_INITIAL_HEALTH;

	private final BossPlane bossPlane;
	private final BossHeartDisplay bossHeartDisplay;

	/**
	 * Constructs a LevelBoss instance with the specified screen dimensions.
	 *
	 * @param screenHeight the height of the screen.
	 * @param screenWidth  the width of the screen.
	 */
	public LevelBoss(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		this.bossPlane = new BossPlane();
		this.bossHeartDisplay = new BossHeartDisplay(Config.BOSS_HEALTH_X_POSITION, Config.BOSS_HEALTH_Y_POSITION, bossPlane.getHealth());
	}

	/**
	 * Gets the name of the level.
	 *
	 * @return the name of the level.
	 */
	@Override
	protected String getLevelName() {
		return "Boss";
	}

	/**
	 * Gets the number of kills needed to defeat the boss.
	 *
	 * @return the required kill count to defeat the boss.
	 */
	@Override
	protected int getKillsNeeded() {
		return 1;
	}

	/**
	 * Provides information about the actors present in this level.
	 *
	 * @return a list of ActorInfo objects describing the actors.
	 */
	@Override
	protected List<ActorInfo> getActorsInfo() {
		return List.of(
				new ActorInfo("F-15", Config.USER_IMAGE, PLAYER_INITIAL_HEALTH, true, true),
				new ActorInfo("C-17", Config.BOSS_IMAGE, 1, false, true),
				new ActorInfo("Hellfire", Config.BOSS_MISSILE, 3, false, false),
				new ActorInfo("Guns", Config.FRIENDLY_GUN, 1, true, false),
				new ActorInfo("Sidewinder", Config.FRIENDLY_MISSILE, 3, true, false)
		);
	}

	/**
	 * Initializes the friendly units, including the player's unit, in the level.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Spawns the boss plane if it has not already been spawned.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0 && !bossPlane.isDestroyed()) {
			addEnemyUnit(bossPlane);
			getRoot().getChildren().add(bossHeartDisplay.getContainer());
			getRoot().getChildren().add(bossPlane.getShieldDisplay());
		}
	}

	/**
	 * Checks if the game is over or if the boss has been defeated.
	 */
	@Override
	protected void checkIfGameOver() {
		bossHeartDisplay.updateHealth(bossPlane.getHealth());
		if (userIsDestroyed()) {
			endGame(false);
		} else if (bossPlane.isDestroyed()) {
			endGame(true);
		}
	}

	/**
	 * Creates and configures the LevelView for the boss level.
	 *
	 * @return the LevelView instance for this level.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, 1);
	}
}
