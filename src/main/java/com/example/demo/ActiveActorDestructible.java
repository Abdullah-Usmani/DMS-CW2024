package com.example.demo;

import com.example.demo.controller.Main;

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private int health;
	private boolean isDestroyed;

	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	public boolean isOutOfBounds(double x, double screenWidth) {
		return (x < 0 || x > screenWidth);
	}

	@Override
	public void updatePosition() {
        double x = getLayoutX() + getTranslateX();
		double y = getLayoutY() + getTranslateY();
		if (isOutOfBounds(x, Main.getScreenWidth())) {
			this.destroy();
		}
	}

	public abstract void updateActor();

	@Override
	public void destroy() {
		setDestroyed();
	}

	protected void setDestroyed() {
		this.isDestroyed = true;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void takeDamage(int damage) {
		health -= damage;
		if (health <= 0) {
			destroy();
		}
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
