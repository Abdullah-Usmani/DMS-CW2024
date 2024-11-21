package com.example.demo;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private final BossHealthDisplay bossHealthDisplay;

    public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		this.boss = new Boss();
		this.boss.setLevelView(levelView);

		// Initialize BossHealthDisplay
		this.bossHealthDisplay = new BossHealthDisplay(850, 25, boss.getHealth());
	}


	@Override
	protected void initializeFriendlyUnits() {
		// Add the player unit
		getRoot().getChildren().add(getUser());

		// Add the BossHealthDisplay to the root node
		getRoot().getChildren().add(bossHealthDisplay.getContainer());
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0 && !boss.isDestroyed()) {
			System.out.println("SPAWNING BOSS");
			addEnemyUnit(boss);
		}
		// Update the boss health display after spawning logic
		bossHealthDisplay.updateHealth(boss.getHealth());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (boss.isDestroyed()) {
			System.out.println("Boss Destroyed");
			winGame();
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}
}