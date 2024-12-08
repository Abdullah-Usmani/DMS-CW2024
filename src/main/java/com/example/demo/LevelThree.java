package com.example.demo;

import java.util.List;

public class LevelThree extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background5.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.LevelBoss";
	private static final int TOTAL_ENEMIES = 10;
	private static final int KILLS_TO_ADVANCE = 20;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final double ENEMY2_SPAWN_PROBABILITY = .10;
	private static final double ENEMY3_SPAWN_PROBABILITY = .05;
	private static final int PLAYER_INITIAL_HEALTH = 15;

	public LevelThree(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	@Override
	protected String getLevelName() {
		return "Three";
	}

	@Override
	protected int getKillsNeeded() {
		return 15;
	}

	@Override
	protected List<ActorInfo> getActorsInfo() {
		return List.of(
			new ActorInfo("F-16", "/com/example/demo/images/enemyplane.png", 1, true),
			new ActorInfo("MiG-29", "/com/example/demo/images/mig-29.png", 2, true),
			new ActorInfo("A-10c", "/com/example/demo/images/a10c.png", 3, true),
			new ActorInfo("Enemy Guns", "/com/example/demo/images/enemyFire.png", 1, false),
			new ActorInfo("R-33", "/com/example/demo/images/enemymissiler33.png", 3, false),
			new ActorInfo("Guns", "/com/example/demo/images/userfire.png", 1, false),
			new ActorInfo("Sidewinder", "/com/example/demo/images/sidewinder.png", 3, false)
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
				EnemyPlane newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
			if (Math.random() < ENEMY2_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				EnemyPlane2 newEnemy = new EnemyPlane2(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
			if (Math.random() < ENEMY3_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				EnemyPlane3 newEnemy = new EnemyPlane3(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
        //		levelView.showLevelOverlay("Three", "Enemy Planes", 20, this::startGame); // Callback triggers game start
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}