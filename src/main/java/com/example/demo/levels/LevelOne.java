package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.actors.EnemyPlane;
import com.example.demo.displays.ActorInfo;

import java.util.*;
import java.util.logging.Logger;

public class LevelOne extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = Config.level1Background;
	private static final String NEXT_LEVEL = Config.level2ClassName;
	private static final int TOTAL_ENEMIES = Config.level1TotalEnemies;
	private static final int KILLS_TO_ADVANCE = Config.level1KillsToAdvance;
	private static final double ENEMY1_SPAWN_PROBABILITY = Config.enemy1SpawnProbability;
	private static final int PLAYER_INITIAL_HEALTH = Config.level1InitialHealth;

	Logger logger = Logger.getLogger(getClass().getName());

	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected String getLevelName() {
		return "One";
	}

	@Override
	protected int getKillsNeeded() {
		return KILLS_TO_ADVANCE;
	}

	@Override
	protected List<ActorInfo> getActorsInfo() {
		return List.of(
				new ActorInfo("F-15", Config.userImage, PLAYER_INITIAL_HEALTH, true, true),
				new ActorInfo("F-16", Config.enemy1Image, 1, false, true),
				new ActorInfo("Enemy Guns", Config.enemyGun, 1, false, false),
				new ActorInfo("Guns", Config.friendlyGun, 1, true, false),
				new ActorInfo("Sidewinder", Config.friendlyMissile, 3, true,false)
		);
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			endGame(false);
		} else if (userHasReachedKillTarget()) {
			goToNextLevel(NEXT_LEVEL);
//			logger.info("Going to next level");
		}
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

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

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, KILLS_TO_ADVANCE);
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}
