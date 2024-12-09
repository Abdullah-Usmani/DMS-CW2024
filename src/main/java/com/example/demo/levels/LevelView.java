package com.example.demo.levels;

import com.example.demo.displays.*;
import javafx.scene.Group;

public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final double KILL_DISPLAY_X_POSITION = 1000;
	private static final double KILL_DISPLAY_Y_POSITION = 25;
	private static final double SHIELD_X_POSITION = 1150;
	private static final double SHIELD_Y_POSITION = 25;
	private static final double BOSS_HEALTH_X_POSITION = 1150;
	private static final double BOSS_HEALTH_Y_POSITION = 75;
	private static final int WIN_IMAGE_X_POSITION = 0;
	private static final int WIN_IMAGE_Y_POSITION = 0;
	private static final int LOSS_SCREEN_X_POSITION = 0;
	private static final int LOSS_SCREEN_Y_POSITION = 0;

	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private final KillDisplay killDisplay;
	private final ShieldImage shieldImage;
	private final BossHealthDisplay bossHealthDisplay;

	public LevelView(Group root, int heartsToDisplay, int killsNeeded) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.killDisplay = new KillDisplay(KILL_DISPLAY_X_POSITION, KILL_DISPLAY_Y_POSITION, killsNeeded);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		this.bossHealthDisplay = new BossHealthDisplay(BOSS_HEALTH_X_POSITION, BOSS_HEALTH_Y_POSITION, 5); // Initialize with 5 health
	}

	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public void showKillDisplay() {
		root.getChildren().add(killDisplay.getContainer());
	}

	public void showBossHealthDisplay() {
		root.getChildren().add(bossHealthDisplay.getContainer());
	}

	public void updateBossHealth(int newHealth) {
		bossHealthDisplay.updateHealth(newHealth);
	}

	public void showWinImage() {
		root.getChildren().add(winImage);
	}

	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	public void updateKills(int newKillCount) {
		killDisplay.updateKills(newKillCount);
	}

	public void showShield() {
		if (!root.getChildren().contains(shieldImage)) {
			root.getChildren().add(shieldImage);
		}
		shieldImage.showShield();
	}

	public void hideShield() {
		shieldImage.hideShield();
	}
}