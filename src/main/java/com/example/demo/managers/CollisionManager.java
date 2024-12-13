/**
 * Manages collision detection and handling for game objects in "F-15: Strike Eagle."
 *
 * The CollisionManager class handles interactions between friendly units, enemy units,
 * and their respective projectiles. It also triggers effects and updates game stats upon collisions.
 */
package com.example.demo.managers;

import com.example.demo.Config;
import com.example.demo.actors.ActiveActorDestructible;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * CollisionManager detects and handles collisions between game objects,
 * updating the game state and triggering effects as necessary.
 */
public class CollisionManager {

    /** The root group for managing game objects in the scene. */
    private final Group root;

    /** Callback to update the hit count. */
    private final Runnable updateHitCount;

    /** Callback to update the kill count. */
    private final Runnable updateKillCount;

    /** Manages visual effects for collisions and destructions. */
    private final EffectManager effectManager;

    /** Manages audio effects for collisions and interactions. */
    private final AudioManager AudioManager;

    /** Lists of game objects for collision detection. */
    private List<ActiveActorDestructible> friendlyUnits;
    private List<ActiveActorDestructible> enemyUnits;
    private List<ActiveActorDestructible> friendlyProjectiles;
    private List<ActiveActorDestructible> enemyProjectiles;

    /**
     * Constructs a CollisionManager with the necessary dependencies.
     *
     * @param root             the root group for managing game objects.
     * @param updateHitCount   the callback to update the hit count.
     * @param updateKillCount  the callback to update the kill count.
     * @param effectManager    the manager for visual effects.
     * @param AudioManager     the manager for audio effects.
     */
    public CollisionManager(Group root, Runnable updateHitCount, Runnable updateKillCount, EffectManager effectManager, AudioManager AudioManager) {
        this.root = root;
        this.updateHitCount = updateHitCount;
        this.updateKillCount = updateKillCount;
        this.effectManager = effectManager;
        this.AudioManager = AudioManager;
    }

    /**
     * Handles all collisions between game objects, triggering effects and updates.
     *
     * @param friendlyUnits         list of friendly units.
     * @param enemyUnits            list of enemy units.
     * @param friendlyProjectiles   list of projectiles fired by friendly units.
     * @param enemyProjectiles      list of projectiles fired by enemy units.
     */
    public void handleAllCollisions(List<ActiveActorDestructible> friendlyUnits, List<ActiveActorDestructible> enemyUnits, List<ActiveActorDestructible> friendlyProjectiles, List<ActiveActorDestructible> enemyProjectiles) {
        this.friendlyUnits = friendlyUnits;
        this.enemyUnits = enemyUnits;
        this.friendlyProjectiles = friendlyProjectiles;
        this.enemyProjectiles = enemyProjectiles;
        handleEnemyProjectileCollisions();
        handlePlaneCollisions();
        handleEnemyPenetration();
        handleFriendlyProjectileCollisions();
    }

    /** Handles collisions between enemy projectiles and friendly units. */
    private void handleEnemyProjectileCollisions() {
        List<CollisionResult> results = detectCollisions(enemyProjectiles, friendlyUnits);
        for (CollisionResult result : results) {
            result.target.takeDamage(result.projectile.getDamage());
            effectManager.createSparkEffect(result.collisionX, result.collisionY, root);
            AudioManager.takeDamageAudio();
        }
    }

    /** Handles collisions between friendly projectiles and enemy units. */
    private void handleFriendlyProjectileCollisions() {
        List<CollisionResult> results = detectCollisions(friendlyProjectiles, enemyUnits);
        for (CollisionResult result : results) {
            result.target.takeDamage(result.projectile.getDamage());
            if (result.target.isDestroyed()) {
                effectManager.createDestructionEffect(result.collisionX, result.collisionY, root);
                AudioManager.destructionAudio();
                updateKillCount.run();
            } else {
                effectManager.createSparkEffect(result.collisionX, result.collisionY, root);
                AudioManager.sparkAudio();
            }
            updateHitCount.run();
        }
    }

    /** Handles collisions between enemy and friendly planes. */
    private void handlePlaneCollisions() {
        List<CollisionResult> results = detectCollisions(enemyUnits, friendlyUnits);
        for (CollisionResult result : results) {
            result.target.takeDamage(1);
            effectManager.createDestructionEffect(result.collisionX, result.collisionY, root);
            AudioManager.collisionAudio();
        }
    }

    /** Handles cases where enemies penetrate friendly defenses. */
    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemyHasPenetratedDefenses(enemy)) {
                for (ActiveActorDestructible friend : friendlyUnits) {
                    friend.takeDamage(1);
                    AudioManager.takeDamageAudio();
                }
                enemy.destroy();
            }
        }
    }

    /**
     * Checks if an enemy has penetrated the friendly defenses.
     *
     * @param enemy the enemy to check.
     * @return true if the enemy has penetrated defenses, false otherwise.
     */
    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > Config.getScreenWidth();
    }

    /**
     * Detects collisions between projectiles and targets.
     *
     * @param projectiles list of projectiles.
     * @param targets     list of potential targets.
     * @return a list of collision results.
     */
    private List<CollisionResult> detectCollisions(List<ActiveActorDestructible> projectiles, List<ActiveActorDestructible> targets) {
        List<CollisionResult> collisions = new ArrayList<>();
        Iterator<ActiveActorDestructible> projectileIterator = projectiles.iterator();

        while (projectileIterator.hasNext()) {
            ActiveActorDestructible projectile = projectileIterator.next();
            for (ActiveActorDestructible target : targets) {
                if (projectile.getBoundsInParent().intersects(target.getBoundsInParent())) {
                    collisions.add(new CollisionResult(projectile, target, target.getLayoutX() + target.getTranslateX(), target.getLayoutY() + target.getTranslateY()));
                    projectileIterator.remove();
                    root.getChildren().remove(projectile);
                    break;
                }
            }
        }
        return collisions;
    }

    /**
     * Represents the result of a collision between two game objects.
     */
    private static class CollisionResult {
        final ActiveActorDestructible projectile;
        final ActiveActorDestructible target;
        final double collisionX;
        final double collisionY;

        /**
         * Constructs a CollisionResult.
         *
         * @param projectile the projectile involved in the collision.
         * @param target     the target involved in the collision.
         * @param collisionX the X-coordinate of the collision.
         * @param collisionY the Y-coordinate of the collision.
         */
        CollisionResult(ActiveActorDestructible projectile, ActiveActorDestructible target, double collisionX, double collisionY) {
            this.projectile = projectile;
            this.target = target;
            this.collisionX = collisionX;
            this.collisionY = collisionY;
        }
    }
}
