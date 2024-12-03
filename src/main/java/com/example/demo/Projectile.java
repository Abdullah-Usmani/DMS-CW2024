package com.example.demo;

public class Projectile extends ActiveActorDestructible {
	private final double horizontalVelocity;
	private final int damageMultiplier;
	private final String imageName;

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, double horizontalVelocity, int damageMultiplier) {
		super(imageName, imageHeight, initialXPos, initialYPos, 1);
		this.horizontalVelocity = horizontalVelocity;
		this.damageMultiplier = damageMultiplier;
        this.imageName = imageName;
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

	public int getDamage() {
        return damageMultiplier;
	}

	public String getImageName() {
        return imageName;
	}
}