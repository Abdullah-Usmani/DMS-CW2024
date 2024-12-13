/**
 * Represents a missile fired by the player's plane.
 * Handles movement and damage characteristics.
 */
package com.example.demo.actors;

import com.example.demo.Config;

public class UserMissile extends Projectile {

    private static final int SCREEN_HEIGHT = Config.getScreenHeight();
    private static final int SCREEN_WIDTH = Config.getScreenWidth();
    private static final String IMAGE_NAME = Config.FRIENDLY_MISSILE;
    private static final double SCALAR = Config.USER_MISSILE_SCALAR;
    private static final int IMAGE_HEIGHT = (int) (SCREEN_HEIGHT * SCALAR);
    private static final int IMAGE_WIDTH = (int) (SCREEN_WIDTH * SCALAR);  
    private static final int HORIZONTAL_VELOCITY = (int) Config.USER_MISSILE_HORIZONTAL_VELOCITY;
    private static final int DAMAGE = Config.USER_MISSILE_DAMAGE;

    /**
     * Constructs a UserMissile instance with the specified initial position.
     *
     * @param initialXPos the initial X position of the missile.
     * @param initialYPos the initial Y position of the missile.
     */
    public UserMissile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, IMAGE_WIDTH, initialXPos, initialYPos, HORIZONTAL_VELOCITY, DAMAGE);
    }

    /**
     * Updates the position of the missile by moving it horizontally.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        super.updatePosition();
    }

    /**
     * Updates the actor by updating its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }
}
