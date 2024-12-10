package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.actors.EnemyPlane;
import com.example.demo.actors.EnemyPlane2;
import com.example.demo.displays.ActorInfo;

import java.util.*;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.levels.LevelThree";
	private static final int TOTAL_ENEMIES = 10;
	private static final int KILLS_TO_ADVANCE = 15;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final double ENEMY2_SPAWN_PROBABILITY = .10;
	private static final int PLAYER_INITIAL_HEALTH = 10;

	public LevelTwo(double screenHeight, double screenWidth) {
			super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected String getLevelName() {
		return "Two";
	}

	@Override
	protected int getKillsNeeded() {
		return KILLS_TO_ADVANCE;
	}

	@Override
	protected List<ActorInfo> getActorsInfo() {
		return List.of(
				new ActorInfo("F-16", "/com/example/demo/images/enemyplane.png", 1, false, true),
				new ActorInfo("MiG-29", "/com/example/demo/images/enemymig-29.png", 2, false, true),
				new ActorInfo("Enemy Guns", "/com/example/demo/images/enemyfire.png", 1, false, false),
				new ActorInfo("Guns", "/com/example/demo/images/userfire.png", 1, true, false),
				new ActorInfo("Sidewinder", "/com/example/demo/images/usersidewinder.png", 3, true,false)
		);
	}


	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget()) {
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
			if (Math.random() < ENEMY2_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				EnemyPlane2 newEnemy = new EnemyPlane2(Config.getScreenWidth(), newEnemyInitialYPosition);
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