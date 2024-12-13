/**
 * Represents the first level in the game "F-15: Strike Eagle."
 *
 * The LevelOne class defines the enemy spawn logic, level-specific configurations,
 * and game progression for the first level.
 */
package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.actors.EnemyPlane;
import com.example.demo.displays.ActorInfo;

import java.util.*;
import java.util.logging.Logger;

/**
 * LevelOne defines the game logic for the first level, including enemy spawns
 * and progression to the next level.
 */
public class LevelOne extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL1_BACKGROUND;
	private static final String NEXT_LEVEL = Config.LEVEL_TWO_CLASS_NAME;
	private static final int TOTAL_ENEMIES = Config.LEVEL_ONE_TOTAL_ENEMIES;
	private static final int KILLS_TO_ADVANCE = Config.LEVEL_ONE_KILLS_TO_ADVANCE;
	private static final double ENEMY1_SPAWN_PROBABILITY = Config.ENEMY1_SPAWN_PROBABILITY;
	private static final int PLAYER_INITIAL_HEALTH = Config.LEVEL_ONE_INITIAL_HEALTH;

	Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * Constructs a LevelOne instance with the specified screen dimensions.
	 *
	 * @param screenHeight the height of the screen.
	 * @param screenWidth  the width of the screen.
	 */
	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Gets the name of the level.
	 *
	 * @return the name of the level.
	 */
	@Override
	protected String getLevelName() {
		return "One";
	}

	/**
	 * Gets the number of kills needed to advance to the next level.
	 *
	 * @return the required kill count to advance.
	 */
	@Override
	protected int getKillsNeeded() {
		return KILLS_TO_ADVANCE;
	}

	/**
	 * Gets the information about actors in this level.
	 *
	 * @return a list of actor information.
	 */
	@Override
	protected List<ActorInfo> getActorsInfo() {
		return List.of(
				new ActorInfo("F-15", Config.USER_IMAGE, PLAYER_INITIAL_HEALTH, true, true),
				new ActorInfo("F-16", Config.ENEMY1_IMAGE, 1, false, true),
				new ActorInfo("Enemy Guns", Config.ENEMY_GUN, 1, false, false),
				new ActorInfo("Guns", Config.FRIENDLY_GUN, 1, true, false),
				new ActorInfo("Sidewinder", Config.FRIENDLY_MISSILE, 3, true,false)
		);
	}

	/**
	 * Checks if the game is over or if the player has met the conditions to advance.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			endGame(false);
		} else if (userHasReachedKillTarget()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Initializes the player's units in the level.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Spawns enemy units based on spawn probabilities and remaining enemies.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY1_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				EnemyPlane newEnemy = new EnemyPlane(Config.getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	/**
	 * Instantiates and configures the level view.
	 *
	 * @return the LevelView for this level.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE);
	}

	/**
	 * Checks if the player has reached the required kill target to advance.
	 *
	 * @return true if the player has met the kill target, false otherwise.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}
