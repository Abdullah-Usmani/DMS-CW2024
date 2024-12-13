/**
 * Displays and manages the shield for an actor in the game "F-15: Strike Eagle."
 *
 * The ShieldDisplay class represents the visual shield element associated with an actor,
 * such as the boss plane, providing methods to show, hide, and update the shield's position.
 */
package com.example.demo.displays;

import com.example.demo.Config;
import com.example.demo.actors.ActiveActor;

/**
 * ShieldDisplay manages the visual representation and behavior of an actor's shield.
 */
public class ShieldDisplay extends ActiveActor {

	/**
	 * Constructs a ShieldDisplay with the specified initial position.
	 *
	 * @param initialXPos the initial x-coordinate of the shield.
	 * @param initialYPos the initial y-coordinate of the shield.
	 */
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
	 * Updates the position of the shield based on the associated actor's center.
	 * This method does not include movement logic; the shield position is updated directly.
	 */
	@Override
	public void updatePosition() {
		// No movement logic here; position is updated directly by the associated actor.
	}

	/**
	 * Updates the layout position of the shield based on the given coordinates.
	 *
	 * @param x the x-coordinate of the associated actor's center.
	 * @param y the y-coordinate of the associated actor's center.
	 */
	public void updatePosition(double x, double y) {
		this.setLayoutX(x - getFitWidth() / 2);
		this.setLayoutY(y - getFitHeight() / 2);
	}
}
