package com.example.demo.actors;

import com.example.demo.Config;

public abstract class ActiveActorDestructible extends ActiveActor {

	private int health; // Health initialized through the constructor
	private int damage; // Health initialized through the constructor
	private final String imageName;
	private boolean isDestroyed;

	public ActiveActorDestructible(String imageName, int imageHeight, int imageWidth, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, imageWidth, initialXPos, initialYPos);
		this.health = health; // Set health via constructor
        this.imageName = imageName;
		this.isDestroyed = false;
	}

	public boolean isOutOfBounds(double x, double screenWidth) {
		return (x < 0 || x > screenWidth);
	}

	@Override
	public void updatePosition() {
		double x = getLayoutX() + getTranslateX();
		double y = getLayoutY() + getTranslateY();
		if (isOutOfBounds(x, Config.getScreenWidth())) {
			this.destroy();
		}
	}

	public abstract void updateActor();

	public void destroy() {
		setDestroyed();
	}

	protected void setDestroyed() {
		this.isDestroyed = true;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * Decrements health based on damage and destroys the object if health is zero or less.
	 *
	 * @param damage Amount of damage to inflict
	 */
	public final void takeDamage(int damage) {
		if (shouldTakeDamage()) {
			int newHealth = getHealth() - damage;
			setHealth(newHealth);
//			System.out.println(this.getClass().getSimpleName() + " took damage: " + damage + ", Health: " + getHealth());
			if (getHealth() <= 0) {
				destroy();
			}
		}
	}

	/**
	 * Determines whether the actor should take damage. Default: Always true.
	 * Can be overridden by subclasses like Boss or Shielded objects.
	 */
	protected boolean shouldTakeDamage() {
		return true;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
//		System.out.println("Updated health for " + this.getClass().getSimpleName() + ": " + this.health);
	}

	public int getDamage() {
		return damage;
	}

	public String getImageName() {
		return imageName;
	}
}
