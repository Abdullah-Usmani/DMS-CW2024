/**
 * Represents a power-up in the game that grants special abilities or boosts to the player.
 * Extends the Projectile class and includes unique power-up types.
 */
package com.example.demo.actors;

import com.example.demo.Config;

public class PowerUp extends Projectile {

    private final PowerUpType powerUpType;

    /**
     * Enum representing the different types of power-ups available in the game.
     */
    public enum PowerUpType {
        /** Grants the player additional health. */
        HEALTH_PACK,
        /** Boosts the player's firing rate temporarily. */
        FIRE_RATE_BOOST
    }

    /**
     * Constructs a PowerUp instance with the specified attributes.
     *
     * @param imageName    the name of the image file representing the power-up.
     * @param imageHeight  the height of the power-up's image.
     * @param imageWidth   the width of the power-up's image.
     * @param initialXPos  the initial X position of the power-up.
     * @param initialYPos  the initial Y position of the power-up.
     * @param powerUpType  the type of power-up (e.g., health pack, fire rate boost).
     */
    public PowerUp(String imageName, int imageHeight, int imageWidth, double initialXPos, double initialYPos, PowerUpType powerUpType) {
        super(imageName, imageHeight, imageWidth, initialXPos, initialYPos, 0, 0);
        this.powerUpType = powerUpType;
    }

    /**
     * Gets the type of power-up represented by this instance.
     *
     * @return the type of power-up.
     */
    public PowerUpType getPowerUpType() {
        return powerUpType;
    }
}