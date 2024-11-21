package com.example.demo;

import com.example.demo.controller.Main;

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

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
//			System.out.println("Class: " + this.getClass().getSimpleName());
//			System.out.println( "Width: " + Main.getScreenHeight() + " Height: " + Main.getScreenWidth());
//			System.out.println("X: " + x + " Y: " + y);
//			System.out.println(this.getClass().getSimpleName() + " exited bounds and was destroyed.");
			this.destroy(); // Mark projectile as destroyed
		}
	}

	public abstract void updateActor();

	@Override
	public abstract void takeDamage();

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
	
}
