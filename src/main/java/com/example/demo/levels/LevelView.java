/**
 * Represents the view for a level in the game "F-15: Strike Eagle."
 *
 * The LevelView class manages the display of game elements such as the player's health,
 * kill count, and game outcomes (win or lose).
 */
package com.example.demo.levels;

import com.example.demo.Config;
import com.example.demo.displays.*;
import javafx.scene.Group;

/**
 * LevelView is responsible for rendering the HUD and managing game-related displays.
 */
public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = Config.HEART_X_POSITION;
	private static final double HEART_DISPLAY_Y_POSITION = Config.HEART_Y_POSITION;
	private static final double KILL_DISPLAY_X_POSITION = Config.KILL_X_POSITION;
	private static final double KILL_DISPLAY_Y_POSITION = Config.KILL_Y_POSITION;

	private final Group root;
	private final WinImage winImage;
	private final LoseImage loseImage;
	private final HeartDisplay heartDisplay;
	private final KillDisplay killDisplay;

	/**
	 * Constructs a LevelView instance with specified configurations.
	 *
	 * @param root           the root group for this level view.
	 * @param heartsToDisplay the number of hearts (lives) to display initially.
	 * @param killsNeeded    the number of kills needed to complete the level.
	 */
	public LevelView(Group root, int heartsToDisplay, int killsNeeded) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.killDisplay = new KillDisplay(KILL_DISPLAY_X_POSITION, KILL_DISPLAY_Y_POSITION, killsNeeded);
		this.winImage = new WinImage();
		this.loseImage = new LoseImage();
	}

	/**
	 * Displays the heart display (player lives) on the screen.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Displays the kill display (enemy kills) on the screen.
	 */
	public void showKillDisplay() {
		root.getChildren().add(killDisplay.getContainer());
	}

	/**
	 * Displays the win image when the player completes the level successfully.
	 */
	public void showWinImage() {
		root.getChildren().add(winImage);
	}

	/**
	 * Displays the lose image when the player fails the level.
	 */
	public void showLoseImage() {
		root.getChildren().add(loseImage);
	}

	/**
	 * Updates the heart display by removing hearts to reflect remaining lives.
	 *
	 * @param heartsRemaining the number of hearts remaining.
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * Updates the kill display with the current kill count.
	 *
	 * @param newKillCount the updated kill count.
	 */
	public void addKills(int newKillCount) {
		killDisplay.updateKills(newKillCount);
	}
}