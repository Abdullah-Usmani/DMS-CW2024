package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.actors.BossPlane;
import com.example.demo.displays.ActorInfo;
import com.example.demo.displays.BossHealthDisplay;

import java.util.List;

public class LevelBoss extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = Config.LEVELBOSS_BACKGROUND;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final BossPlane bossPlane;
	private final BossHealthDisplay bossHealthDisplay;

    public LevelBoss(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

		this.bossPlane = new BossPlane();
		this.bossPlane.setLevelView(levelView);
		this.bossHealthDisplay = new BossHealthDisplay(850, 25, bossPlane.getHealth());
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
				new ActorInfo("C-17", Config.BOSS_IMAGE, 1, false,true),
				new ActorInfo("R-33", Config.BOSS_MISSILE, 3, false, false),
				new ActorInfo("Guns", Config.FRIENDLY_GUN, 1, true, false),
				new ActorInfo("Sidewinder", Config.FRIENDLY_MISSILE, 3, true, false)
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
			System.out.println("SPAWNING BOSS");
			addEnemyUnit(bossPlane);
			getRoot().getChildren().add(bossHealthDisplay.getContainer());
		}
	}

	@Override
	protected void checkIfGameOver() {
		// Update the boss health display after spawning logic
		bossHealthDisplay.updateHealth(bossPlane.getHealth());
		if (userIsDestroyed()) {
			loseGame();
		} else if (bossPlane.isDestroyed()) {
			System.out.println("Boss Destroyed");
			winGame();
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
        //		levelView.showLevelOverlay("Boss", "Boss", 15, this::startGame); // Callback triggers game start
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, 1);
	}
}