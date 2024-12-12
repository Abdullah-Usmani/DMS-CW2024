package com.example.demo.managers;

import com.example.demo.Config;
import com.example.demo.actors.ActiveActorDestructible;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollisionManager {

    private final Group root;
    private final Runnable updateHitCount;
    private final Runnable updateKillCount;
    private final EffectManager effectManager;
    private final AudioManager AudioManager;

    private List<ActiveActorDestructible> friendlyUnits;
    private List<ActiveActorDestructible> enemyUnits;
    private List<ActiveActorDestructible> friendlyProjectiles;
    private List<ActiveActorDestructible> enemyProjectiles;

    public CollisionManager(Group root, Runnable updateHitCount, Runnable updateKillCount, EffectManager effectManager, AudioManager AudioManager) {
        this.root = root;
        this.updateHitCount = updateHitCount;
        this.updateKillCount = updateKillCount;
        this.effectManager = effectManager;
        this.AudioManager = AudioManager;
    }

    public void handleAllCollisions( List<ActiveActorDestructible> friendlyUnits, List<ActiveActorDestructible> enemyUnits, List<ActiveActorDestructible> friendlyProjectiles, List<ActiveActorDestructible> enemyProjectiles) {
        this.friendlyUnits = friendlyUnits;
        this.enemyUnits = enemyUnits;
        this.friendlyProjectiles = friendlyProjectiles;
        this.enemyProjectiles = enemyProjectiles;
        handleEnemyProjectileCollisions();
        handlePlaneCollisions();
        handleEnemyPenetration();
        handleFriendlyProjectileCollisions();
    }

    private void handleEnemyProjectileCollisions() {
        List<CollisionResult> results = detectCollisions(enemyProjectiles, friendlyUnits);
        for (CollisionResult result : results) {
            result.target.takeDamage(result.projectile.getDamage());
            effectManager.createSparkEffect(result.collisionX, result.collisionY, root);
            AudioManager.takeDamageAudio();
        }
    }

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

    private void handlePlaneCollisions() {
        List<CollisionResult> results = detectCollisions(enemyUnits, friendlyUnits);
        for (CollisionResult result : results) {
            result.target.takeDamage(1);
            effectManager.createDestructionEffect(result.collisionX, result.collisionY, root);
            AudioManager.collisionAudio();
        }
    }

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

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > Config.getScreenWidth();
    }

    private List<CollisionResult> detectCollisions(List<ActiveActorDestructible> projectiles,
                                                   List<ActiveActorDestructible> targets) {
        List<CollisionResult> collisions = new ArrayList<>();
        Iterator<ActiveActorDestructible> projectileIterator = projectiles.iterator();

        while (projectileIterator.hasNext()) {
            ActiveActorDestructible projectile = projectileIterator.next();
            for (ActiveActorDestructible target : targets) {
                if (projectile.getBoundsInParent().intersects(target.getBoundsInParent())) {
                    collisions.add(new CollisionResult(projectile, target, target.getLayoutX() + target.getTranslateX(), target.getLayoutY() +  target.getTranslateY()));
                    projectileIterator.remove();
                    root.getChildren().remove(projectile);
                    break;
                }
            }
        }
        return collisions;
    }

    private static class CollisionResult {
        final ActiveActorDestructible projectile;
        final ActiveActorDestructible target;
        final double collisionX;
        final double collisionY;

        CollisionResult(ActiveActorDestructible projectile, ActiveActorDestructible target, double collisionX, double collisionY) {
            this.projectile = projectile;
            this.target = target;
            this.collisionX = collisionX;
            this.collisionY = collisionY;
        }
    }
}
