package com.example.demo.displays;

import com.example.demo.Config;
import com.example.demo.actors.ActiveActor;

public class ShieldDisplay extends ActiveActor {

	public ShieldDisplay(double initialXPos, double initialYPos) {
		super(Config.SHIELD_IMAGE, (int) Config.SHIELD_SIZE, (int) Config.SHIELD_SIZE, initialXPos, initialYPos);
		this.setVisible(false); // Initially invisible
	}

	/**
	 * Makes the shield visible.
	 */
	public void showShield() {
		this.setVisible(true);
	}

	/**
	 * Hides the shield.
	 */
	public void hideShield() {
		this.setVisible(false);
	}

	/**
	 * Updates the position of the shield to match the associated actor.
	 * This implementation overrides the abstract method in ActiveActor.
	 */
	@Override
	public void updatePosition() {
		// No movement logic here; position is updated directly by the boss plane.
	}

	/**
	 * Updates the shield's layout position based on the associated actor's center.
	 *
	 * @param x The x-coordinate of the associated actor's center.
	 * @param y The y-coordinate of the associated actor's center.
	 */
	public void updatePosition(double x, double y) {
		this.setLayoutX(x - getFitWidth() / 2);
		this.setLayoutY(y - getFitHeight() / 2);
	}
}