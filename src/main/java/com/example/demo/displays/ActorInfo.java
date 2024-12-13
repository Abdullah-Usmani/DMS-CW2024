/**
 * Represents information about an actor in the game "F-15: Strike Eagle."
 *
 * The ActorInfo class encapsulates metadata for a game actor, including its name, image path,
 * health or damage value, and its classification as a friendly or enemy actor.
 */
package com.example.demo.displays;

/**
 * ActorInfo provides a structured representation of an actor's properties and classification.
 */
public class ActorInfo {

    /** The name of the actor (e.g., "F-16"). */
    public final String name;

    /** The path to the actor's image resource. */
    public final String imagePath;

    /** The stat value of the actor (e.g., health for planes, damage for projectiles). */
    public final int statValue;

    /** Indicates whether the actor is friendly (true) or an enemy (false). */
    public final boolean isFriendly;

    /** Indicates whether the actor is a plane (true) or a projectile (false). */
    public final boolean isPlane;

    /**
     * Constructs an ActorInfo instance with the specified parameters.
     *
     * @param name       the name of the actor.
     * @param imagePath  the path to the actor's image resource.
     * @param statValue  the stat value of the actor (health or damage).
     * @param isFriendly true if the actor is friendly, false if it is an enemy.
     * @param isPlane    true if the actor is a plane, false if it is a projectile.
     */
    public ActorInfo(String name, String imagePath, int statValue, boolean isFriendly, boolean isPlane) {
        this.name = name;
        this.imagePath = imagePath;
        this.statValue = statValue;
        this.isFriendly = isFriendly;
        this.isPlane = isPlane;
    }
}