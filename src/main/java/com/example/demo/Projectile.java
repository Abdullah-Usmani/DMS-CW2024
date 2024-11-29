package com.example.demo;

public class Projectile extends ActiveActorDestructible {
	private final double horizontalVelocity;
	private final int damageMultiplier;

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, double horizontalVelocity, int damageMultiplier) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.horizontalVelocity = horizontalVelocity;
		this.damageMultiplier = damageMultiplier;
	}

	@Override
	public void updatePosition() {
		moveHorizontally(horizontalVelocity);
		super.updatePosition();
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

	@Override
	public void takeDamage() {
        super.takeDamage(damageMultiplier);
	}
}