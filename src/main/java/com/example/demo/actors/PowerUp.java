package com.example.demo.actors;

import com.example.demo.Config;

public class PowerUp extends Projectile {

    private final PowerUpType powerUpType;

    public enum PowerUpType {
        HEALTH_PACK,
        FIRE_RATE_BOOST
    }

    public PowerUp(String imageName, int imageHeight, int imageWidth, double initialXPos, double initialYPos, PowerUpType powerUpType) {
        super(imageName, imageHeight, imageWidth, initialXPos, initialYPos, 0, 0);
        this.powerUpType = powerUpType;
    }

    public PowerUpType getPowerUpType() {
        return powerUpType;
    }
}
