package com.example.demo;

public class ActorInfo {
    public final String name;       // Name of the actor (e.g., "F-16")
    public final String imagePath;  // Path to the actor's image
    public final int statValue;     // Health or damage value
    public final boolean isPlane;   // True if it's a plane (health), false if it's a projectile (damage)

    public ActorInfo(String name, String imagePath, int statValue, boolean isPlane) {
        this.name = name;
        this.imagePath = imagePath;
        this.statValue = statValue;
        this.isPlane = isPlane;
    }

//    @Override
//    public String toString() {
//        return "ActorInfo{" +
//                "name='" + name + '\'' +
//                ", imagePath='" + imagePath +
//                ", statValue=" + statValue +
//                ", isPlane=" + isPlane +
//                '}';
//    }
}