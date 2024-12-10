package com.example.demo.displays;

public class ActorInfo {
    public final String name;       // Name of the actor (e.g., "F-16")
    public final String imagePath;  // Path to the actor's image
    public final int statValue;     // Health or damage value
    public final boolean isFriendly;   // True if it's a friendly (user), false if it's an enemy
    public final boolean isPlane;   // True if it's a plane (health), false if it's a projectile (damage)

    public ActorInfo(String name, String imagePath, int statValue, boolean isFriendly, boolean isPlane) {
        this.name = name;
        this.imagePath = imagePath;
        this.statValue = statValue;
        this.isFriendly = isFriendly;
        this.isPlane = isPlane;
    }
}