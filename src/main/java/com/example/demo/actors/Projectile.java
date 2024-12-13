/**
 * Represents a projectile fired by a plane in the game.
 * Handles movement, damage, and image properties.
 */
package com.example.demo.actors;

public class Projectile extends ActiveActorDestructible {

	private final double horizontalVelocity;
	private final int damageMultiplier;
	private final String imageName;

	/**
	 * Constructs a Projectile instance with the specified attributes.
	 *
	 * @param imageName          the name of the image file representing the projectile.
	 * @param imageHeight        the height of the projectile's image.
	 * @param imageWidth         the width of the projectile's image.
	 * @param initialXPos        the initial X position of the projectile.
	 * @param initialYPos        the initial Y position of the projectile.
	 * @param horizontalVelocity the horizontal velocity of the projectile.
	 * @param damageMultiplier   the damage multiplier of the projectile.
	 */
	public Projectile(String imageName, int imageHeight, int imageWidth, double initialXPos, double initialYPos, double horizontalVelocity, int damageMultiplier) {
		super(imageName, imageHeight, imageWidth, initialXPos, initialYPos, 1);
		this.horizontalVelocity = horizontalVelocity;
		this.damageMultiplier = damageMultiplier;
		this.imageName = imageName;
	}

	/**
	 * Updates the position of the projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(horizontalVelocity);
		super.updatePosition();
	}

	/**
	 * Updates the actor by updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Gets the damage value of the projectile.
	 *
	 * @return the damage multiplier of the projectile.
	 */
	@Override
	public int getDamage() {
		return damageMultiplier;
	}

	/**
	 * Gets the name of the image file representing the projectile.
	 *
	 * @return the image name of the projectile.
	 */
	@Override
	public String getImageName() {
		return imageName;
	}
}