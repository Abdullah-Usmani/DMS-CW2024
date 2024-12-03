package com.example.demo;

public class LevelBoss extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background7.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final BossPlane bossPlane;
	private final BossHealthDisplay bossHealthDisplay;

    public LevelBoss(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		this.bossPlane = new BossPlane();
		this.bossPlane.setLevelView(levelView);

		// Initialize BossHealthDisplay
		this.bossHealthDisplay = new BossHealthDisplay(850, 25, bossPlane.getHealth());
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
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}
}