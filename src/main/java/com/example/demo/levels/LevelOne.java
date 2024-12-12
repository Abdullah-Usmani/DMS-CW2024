package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.actors.EnemyPlane;
import com.example.demo.displays.ActorInfo;

import java.util.*;

public class LevelOne extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = Config.LEVEL1_BACKGROUND;
	private static final String NEXT_LEVEL = "com.example.demo.levels.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		System.out.println("BG Image name: " + BACKGROUND_IMAGE_NAME);
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
				new ActorInfo("F-16", Config.ENEMY1_IMAGE, 1, false, true),
				new ActorInfo("Enemy Guns", Config.ENEMY_GUN, 1, false, false),
				new ActorInfo("Guns", Config.FRIENDLY_GUN, 1, true, false),
				new ActorInfo("Sidewinder", Config.FRIENDLY_MISSILE, 3, true,false)
		);
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
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
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
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
