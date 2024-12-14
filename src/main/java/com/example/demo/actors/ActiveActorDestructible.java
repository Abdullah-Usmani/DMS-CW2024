/**
 * Represents an active actor in the game that can be destroyed.
 * This abstract class extends ActiveActor and includes additional attributes for health and destruction logic.
 */
package com.example.demo.actors;

import com.example.demo.Config;

public abstract class ActiveActorDestructible extends ActiveActor {

	private int health; // Health initialized through the constructor
	private int damage; // Damage initialized through the constructor
	private final String imageName;
	private boolean isDestroyed;

	/**
	 * Constructs an ActiveActorDestructible instance with the specified properties.
	 *
	 * @param imageName    the name of the image file representing the actor.
	 * @param imageHeight  the height of the actor's image.
	 * @param imageWidth   the width of the actor's image.
	 * @param initialXPos  the initial X position of the actor.
	 * @param initialYPos  the initial Y position of the actor.
	 * @param health       the initial health of the actor.
	 */
	protected ActiveActorDestructible(String imageName, int imageHeight, int imageWidth, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, imageWidth, initialXPos, initialYPos);
		this.health = health;
		this.imageName = imageName;
		this.isDestroyed = false;
	}

	/**
	 * Checks if the actor is out of bounds on the screen.
	 *
	 * @param x           the current X position of the actor.
	 * @param screenWidth the width of the screen.
	 * @return true if the actor is out of bounds, false otherwise.
	 */
	public boolean isOutOfBounds(double x, double screenWidth) {
		return (x < 0 || x > screenWidth);
	}

	/**
	 * Updates the position of the actor. If it moves out of bounds, it is destroyed.
	 */
	@Override
	public void updatePosition() {
		double x = getLayoutX() + getTranslateX();
		double y = getLayoutY() + getTranslateY();
		if (isOutOfBounds(x, Config.getScreenWidth())) {
			this.destroy();
		}
	}

	/**
	 * Abstract method to update the actor's state. Must be implemented by subclasses.
	 */
	public abstract void updateActor();

	/**
	 * Marks the actor as destroyed.
	 */
	public void destroy() {
		setDestroyed();
	}

	/**
	 * Sets the destruction state of the actor.
	 */
    public void setDestroyed() {
		this.isDestroyed = true;
	}

	/**
	 * Checks if the actor is destroyed.
	 *
	 * @return true if the actor is destroyed, false otherwise.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * Reduces the health of the actor by a specified damage amount. If health reaches zero or below, the actor is destroyed.
	 *
	 * @param damage the amount of damage to inflict.
	 */
	public final void takeDamage(int damage) {
		if (shouldTakeDamage()) {
			int newHealth = getHealth() - damage;
			setHealth(newHealth);
			if (getHealth() <= 0) {
				destroy();
			}
		}
	}

	/**
	 * Determines whether the actor should take damage. Default implementation always returns true.
	 * Can be overridden by subclasses like Boss or Shielded objects.
	 *
	 * @return true if the actor should take damage, false otherwise.
	 */
	protected boolean shouldTakeDamage() {
		return true;
	}

	/**
	 * Sets the health of the actor.
	 *
	 * @param health the new health value.
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Gets the current health of the actor.
	 *
	 * @return the health of the actor.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Gets the damage value associated with the actor.
	 *
	 * @return the damage value.
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Gets the name of the image file representing the actor.
	 *
	 * @return the image name.
	 */
	public String getImageName() {
		return imageName;
	}
}