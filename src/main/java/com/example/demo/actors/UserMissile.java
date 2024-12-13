package com.example.demo.actors;

import com.example.demo.Config;

public class UserMissile extends Projectile {
    private static final int SCREEN_HEIGHT = Config.getScreenHeight();
    private static final int SCREEN_WIDTH = Config.getScreenWidth();
    private static final String IMAGE_NAME = Config.friendlyMissile;
    private static final double SCALAR =  Config.userMissileScalar;
    private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * SCALAR);
    private static final int IMAGE_WIDTH =  (int) (SCREEN_WIDTH * SCALAR);  // Dynamically get width
    private static final int HORIZONTAL_VELOCITY = (int) Config.userMissileHorizontalVelocity;
    private static final int DAMAGE = Config.userMissileDamage;

    public UserMissile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, IMAGE_WIDTH, initialXPos, initialYPos, HORIZONTAL_VELOCITY, DAMAGE);
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
