package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.actors.EnemyPlane;
import com.example.demo.displays.ActorInfo;

import java.util.*;

public class LevelOne extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL1_BACKGROUND;
	private static final String NEXT_LEVEL = Config.LEVEL_TWO_CLASS_NAME;
	private static final int TOTAL_ENEMIES = Config.LEVEL_ONE_TOTAL_ENEMIES;
	private static final int KILLS_TO_ADVANCE = Config.LEVEL_ONE_KILLS_TO_ADVANCE;
	private static final double ENEMY1_SPAWN_PROBABILITY = Config.ENEMY1_SPAWN_PROBABILITY;
	private static final int PLAYER_INITIAL_HEALTH = Config.LEVEL_ONE_INITIAL_HEALTH;

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
				new ActorInfo("F-15", Config.USER_IMAGE, PLAYER_INITIAL_HEALTH, true, true),
				new ActorInfo("F-16", Config.ENEMY1_IMAGE, 1, false, true),
				new ActorInfo("Enemy Guns", Config.ENEMY_GUN, 1, false, false),
				new ActorInfo("Guns", Config.FRIENDLY_GUN, 1, true, false),
				new ActorInfo("Sidewinder", Config.FRIENDLY_MISSILE, 3, true,false)
		);
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			endGame(false);
		} else if (userHasReachedKillTarget()) {
			goToNextLevel(NEXT_LEVEL);
			System.out.println("Going to next level");
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
