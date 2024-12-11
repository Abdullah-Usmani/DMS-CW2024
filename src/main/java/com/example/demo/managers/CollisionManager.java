package com.example.demo.managers;

import com.example.demo.Config;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.displays.ExplosionEffect;
import javafx.scene.Group;

import java.util.Iterator;
import java.util.List;

public class CollisionManager {

    private final Group root;
    private final Runnable updateHitCount;
    private final Runnable updateKillCount;

    private List<ActiveActorDestructible> friendlyUnits;
    private List<ActiveActorDestructible> enemyUnits;
    private List<ActiveActorDestructible> friendlyProjectiles;
    private List<ActiveActorDestructible> enemyProjectiles;

    public CollisionManager(Group root, Runnable updateHitCount, Runnable updateKillCount) {
        this.root = root;
        this.updateHitCount = updateHitCount;
        this.updateKillCount = updateKillCount;
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

    public void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    public void handleFriendlyProjectileCollisions() {
        if (handleCollisions(friendlyProjectiles, enemyUnits)) {
            updateHitCount.run();
            for (ActiveActorDestructible enemy : enemyUnits) {
                if (enemy.isDestroyed()) {
                    updateKillCount.run();
                }
            }
        }
    }

    public void handlePlaneCollisions() {
        if (handleCollisions(enemyUnits, friendlyUnits)) {
            for (ActiveActorDestructible friend : friendlyUnits) {
                friend.takeDamage(1);
//                ExplosionEffect effect = new ExplosionEffect("/com/example/demo/images/explosion1.png", 50, 50, 1.0, "/com/example/demo/audio/fortnite-pump.mp3");
            }
        }
    }

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > Config.getScreenWidth();
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
                for (ActiveActorDestructible friend : friendlyUnits) {
                    friend.takeDamage(1);
                }
				enemy.destroy();
			}
		}
	}

    public boolean handleCollisions(List<ActiveActorDestructible> projectiles,
                                 List<ActiveActorDestructible> targets) {
        Iterator<ActiveActorDestructible> projectileIterator = projectiles.iterator();
        boolean collisionOccurred = false;
        while (projectileIterator.hasNext()) {
            ActiveActorDestructible projectile = projectileIterator.next();
            for (ActiveActorDestructible target : targets) {
                if (projectile.getBoundsInParent().intersects(target.getBoundsInParent())) {
                    target.takeDamage(projectile.getDamage());
//                    ExplosionEffect effect = new ExplosionEffect(
//                            "/com/example/demo/images/explosion1.png",
//                            50, 50, 1.0,
//                            "/com/example/demo/audio/explosion.mp3"
//                    );
//                    effect.createEffect(
//                            projectile.getLayoutX() + projectile.getTranslateX(),
//                            projectile.getLayoutY() + projectile.getTranslateY(),
//                            root
//                    );
                    projectileIterator.remove();
                    root.getChildren().remove(projectile);
                    collisionOccurred = true;
                    break;
                }
            }
        }
        return collisionOccurred;
    }

    //	private boolean handleCollisions(List<ActiveActorDestructible> projectiles, List<ActiveActorDestructible> targets) {
//		Iterator<ActiveActorDestructible> projectileIterator = projectiles.iterator();
//		boolean collisionOccurred = false;
//		while (projectileIterator.hasNext()) {
//			ActiveActorDestructible projectile = projectileIterator.next();
//			for (ActiveActorDestructible target : targets) {
//				if (projectile.getBoundsInParent().intersects(target.getBoundsInParent())) {
//					target.takeDamage(projectile.getDamage());
//					double collisionX = projectile.getLayoutX() + projectile.getTranslateX();
//					double collisionY = projectile.getLayoutY() + projectile.getTranslateY();
//					handleCollisionEffects(collisionX, collisionY, projectile.getImageName());
//
//					// Specific logic for enemy planes
//					if (target instanceof EnemyPlane ||
//							target instanceof EnemyPlane2 ||
//							target instanceof EnemyPlane3 ||
//							target instanceof BossPlane) {
//
//						if (target.isDestroyed()) { // Check if the enemy unit is destroyed
//							updateKillCount(true); // Increment the kill count
//						}
//					}
//					projectileIterator.remove();
//					root.getChildren().remove(projectile);
//					collisionOccurred = true;
//					break;
//				}
//			}
//		}
//		return collisionOccurred;
//	}

//	private void handleCollisionEffects(double x, double y, String collisionType) {
//		ExplosionEffect explosionEffect;
//
//		switch (collisionType) {
//			case "userfire.png":
//				explosionEffect = new ExplosionEffect(
//						"/com/example/demo/images/explosion1.png",
//						50, 50, 1.0,
//						"/com/example/demo/audio/ricochet-1.mp3"
//				);
//				break;
//			case "userplane.png":
//				explosionEffect = new ExplosionEffect(
//						"/com/example/demo/images/explosion1.png",
//						75, 75, 1.5,
//						"/com/example/demo/audio/fortnite-pump.mp3"
//				);
//				break;
//			case "usersidewinder.png":
//				explosionEffect = new ExplosionEffect(
//						"/com/example/demo/images/explosion1.png",
//						100, 100, 1.5,
//						"/com/example/demo/audio/roblox-explosion.mp3"
//				);
//				break;
//			default:
//				explosionEffect = new ExplosionEffect(
//						"/com/example/demo/images/explosion1.png",
//						50, 50, 1.0,
//						"/com/example/demo/audio/tf2-crit.mp3"
//				);
//		}
//
//		// Trigger the effect
//		explosionEffect.createEffect(x, y, root);
//	}

}
