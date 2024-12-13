package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.actors.BossPlane;
import com.example.demo.displays.ActorInfo;
import com.example.demo.displays.BossHealthDisplay;

import java.util.List;

public class LevelBoss extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = Config.levelBossBackground;
	private static final int PLAYER_INITIAL_HEALTH = Config.levelBossInitialHealth;
	private final BossPlane bossPlane;
	private final BossHealthDisplay bossHealthDisplay;

    public LevelBoss(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

		this.bossPlane = new BossPlane();
		this.bossHealthDisplay = new BossHealthDisplay(Config.bossHealthXPosition, Config.bossHealthYPosition, bossPlane.getHealth());
	}

	@Override
	protected String getLevelName() {
		return "Boss";
	}

	@Override
	protected int getKillsNeeded() {
		return 1;
	}

	@Override
	protected List<ActorInfo> getActorsInfo() {
		return List.of(
				new ActorInfo("F-15", Config.userImage, PLAYER_INITIAL_HEALTH, true, true),
				new ActorInfo("C-17", Config.bossImage, 1, false,true),
				new ActorInfo("R-33", Config.bossMissile, 3, false, false),
				new ActorInfo("Guns", Config.friendlyGun, 1, true, false),
				new ActorInfo("Sidewinder", Config.friendlyMissile, 3, true, false)
		);
	}

	@Override
	protected void initializeFriendlyUnits() {
		// Add the player unit
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0 && !bossPlane.isDestroyed()) {
			addEnemyUnit(bossPlane);
			getRoot().getChildren().add(bossHealthDisplay.getContainer());
			getRoot().getChildren().add(bossPlane.getShieldDisplay());
		}
	}

	@Override
	protected void checkIfGameOver() {
		// Update the boss health display after spawning logic
		bossHealthDisplay.updateHealth(bossPlane.getHealth());
		if (userIsDestroyed()) {
			endGame(false);
		} else if (bossPlane.isDestroyed()) {
			endGame(true);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
        //		levelView.showLevelOverlay("Boss", "Boss", 15, this::startGame); // Callback triggers game start
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, 1);
	}
}