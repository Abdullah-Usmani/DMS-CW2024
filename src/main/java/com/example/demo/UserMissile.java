package com.example.demo;

import com.example.demo.controller.Main;

public class UserMissile extends Projectile {
    private static final int SCREEN_HEIGHT = Main.getScreenHeight();
    private static final int SCREEN_WIDTH = Main.getScreenWidth();
    private static final String IMAGE_NAME = "sidewinder.png";
    private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * .01);
    private static final double HORIZONTAL_VELOCITY = (SCREEN_WIDTH * .01);
    private static final int DAMAGE_MULTIPLIER = 3;

    public UserMissile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, HORIZONTAL_VELOCITY, DAMAGE_MULTIPLIER);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        super.updatePosition();
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

}
