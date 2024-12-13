package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.displays.*;
import javafx.scene.Group;

public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = Config.heartXPosition;
	private static final double HEART_DISPLAY_Y_POSITION = Config.heartYPosition;
	private static final double KILL_DISPLAY_X_POSITION = Config.killXPosition;
	private static final double KILL_DISPLAY_Y_POSITION = Config.killYPosition;

	private final Group root;
	private final WinImage winImage;
	private final LoseImage loseImage;
	private final HeartDisplay heartDisplay;
	private final KillDisplay killDisplay;

	public LevelView(Group root, int heartsToDisplay, int killsNeeded) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.killDisplay = new KillDisplay(KILL_DISPLAY_X_POSITION, KILL_DISPLAY_Y_POSITION, killsNeeded);
		this.winImage = new WinImage();
		this.loseImage = new LoseImage();
	}

	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public void showKillDisplay() {
		root.getChildren().add(killDisplay.getContainer());
	}

	public void showWinImage() {
		root.getChildren().add(winImage);
	}

	public void showLoseImage() {
		root.getChildren().add(loseImage);
	}

	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	public void addKills(int newKillCount) {
		killDisplay.updateKills(newKillCount);
	}
}