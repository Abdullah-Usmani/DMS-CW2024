package com.example.demo.managers;

import com.example.demo.Config;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.Projectile;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.Group;

public class CollisionManagerTest {

    private CollisionManager collisionManager;
    private Group root;
    private List<String> effectsTriggered;
    private List<String> soundsPlayed;
    private int hitCount;
    private int killCount;

    @BeforeAll
    static void initJavaFX() {
        // Initialize the JavaFX toolkit, as JavaFX components are used in the tests.
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() {
        // Set up test environment: create necessary objects and reset counters.
        root = new Group();
        effectsTriggered = new ArrayList<>();
        soundsPlayed = new ArrayList<>();
        hitCount = 0;
        killCount = 0;

        // Create a CollisionManager with test callbacks and mocked EffectManager and AudioManager.
        collisionManager = new CollisionManager(
                root,
                () -> hitCount++, // Increment hit count when callback is triggered.
                () -> killCount++, // Increment kill count when callback is triggered.
                new EffectManager() {
                    @Override
                    public void createSparkEffect(double x, double y, Group root) {
                        effectsTriggered.add("SparkEffect"); // Track triggered effects.
                    }

                    @Override
                    public void createDestructionEffect(double x, double y, Group root) {
                        effectsTriggered.add("DestructionEffect");
                    }
                },
                new AudioManager() {
                    @Override
                    public void sparkAudio() {
                        soundsPlayed.add("SparkAudio"); // Track played sounds.
                    }

                    @Override
                    public void destructionAudio() {
                        soundsPlayed.add("DestructionAudio");
                    }

                    @Override
                    public void collisionAudio() {
                        soundsPlayed.add("CollisionAudio");
                    }

                    @Override
                    public void takeDamageAudio() {
                        soundsPlayed.add("TakeDamageAudio");
                    }
                }
        );
    }

    @Test
    public void testHandleEnemyProjectileCollisions() {
        // Test collisions between enemy projectiles and friendly units.

        // Create a friendly unit and an enemy projectile.
        ActiveActorDestructible friendlyUnit = createDestructibleActor(100);
        ActiveActorDestructible enemyProjectile = createProjectile(20);

        // Set positions to simulate a collision.
        friendlyUnit.setLayoutX(100);
        friendlyUnit.setLayoutY(100);
        enemyProjectile.setLayoutX(100);
        enemyProjectile.setLayoutY(100);

        // Add units to lists for collision detection.
        List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
        friendlyUnits.add(friendlyUnit);

        List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>();
        enemyProjectiles.add(enemyProjectile);

        // Perform collision handling.
        collisionManager.handleAllCollisions(friendlyUnits, new ArrayList<>(), new ArrayList<>(), enemyProjectiles);

        // Verify that the friendly unit took damage and the correct effects and sounds were triggered.
        assertEquals(80, friendlyUnit.getHealth());
        assertTrue(effectsTriggered.contains("SparkEffect"));
        assertTrue(soundsPlayed.contains("TakeDamageAudio"));
    }

    @Test
    public void testHandleFriendlyProjectileCollisions() {
        // Test collisions between friendly projectiles and enemy units.

        // Create an enemy unit and two friendly projectiles.
        ActiveActorDestructible enemyUnit = createDestructibleActor(50);
        ActiveActorDestructible friendlyProjectile = createProjectile(30);
        ActiveActorDestructible friendlyProjectile2 = createProjectile(30);

        // Set positions to simulate a collision.
        enemyUnit.setLayoutX(100);
        enemyUnit.setLayoutY(100);
        friendlyProjectile.setLayoutX(100);
        friendlyProjectile.setLayoutY(100);

        // Add units to lists for collision detection.
        List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
        enemyUnits.add(enemyUnit);

        List<ActiveActorDestructible> friendlyProjectiles = new ArrayList<>();
        friendlyProjectiles.add(friendlyProjectile);
        friendlyProjectiles.add(friendlyProjectile2);

        // Perform collision handling for the first projectile.
        collisionManager.handleAllCollisions(new ArrayList<>(), enemyUnits, friendlyProjectiles, new ArrayList<>());

        // Verify that the enemy unit took damage, and the correct effects and sounds were triggered.
        assertEquals(20, enemyUnit.getHealth());
        assertTrue(effectsTriggered.contains("SparkEffect"));
        assertTrue(soundsPlayed.contains("SparkAudio"));
        assertEquals(1, hitCount);
        assertEquals(0, killCount);

        // Simulate another collision with the second projectile.
        enemyUnit.setLayoutX(150);
        enemyUnit.setLayoutY(150);
        friendlyProjectile2.setLayoutX(150);
        friendlyProjectile2.setLayoutY(150);

        collisionManager.handleAllCollisions(new ArrayList<>(), enemyUnits, friendlyProjectiles, new ArrayList<>());

        // Verify that the enemy unit is destroyed and the correct effects and sounds were triggered.
        assertEquals(-10, enemyUnit.getHealth());
        assertTrue(effectsTriggered.contains("DestructionEffect"));
        assertTrue(soundsPlayed.contains("DestructionAudio"));
        assertEquals(1, killCount);
    }

    @Test
    public void testHandlePlaneCollisions() {
        // Test collisions between friendly and enemy planes.

        // Create a friendly unit and an enemy unit.
        ActiveActorDestructible friendlyUnit = createDestructibleActor(100);
        ActiveActorDestructible enemyUnit = createDestructibleActor(100);

        // Add units to lists for collision detection.
        List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
        friendlyUnits.add(friendlyUnit);

        List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
        enemyUnits.add(enemyUnit);

        // Set positions to simulate a collision.
        friendlyUnit.setLayoutX(100);
        friendlyUnit.setLayoutY(100);
        enemyUnit.setLayoutX(100);
        enemyUnit.setLayoutY(100);

        // Perform collision handling.
        collisionManager.handleAllCollisions(friendlyUnits, enemyUnits, new ArrayList<>(), new ArrayList<>());

        // Verify that the friendly unit took damage and the correct effects and sounds were triggered.
        assertEquals(99, friendlyUnit.getHealth());
        assertTrue(effectsTriggered.contains("DestructionEffect"));
        assertTrue(soundsPlayed.contains("CollisionAudio"));
    }

    @Test
    public void testHandleEnemyPenetration() {
        // Test when an enemy penetrates friendly defenses.

        // Create an enemy unit and a friendly unit.
        ActiveActorDestructible enemyUnit = createDestructibleActor(50);
        enemyUnit.setTranslateX(2000); // Simulate penetration beyond screen width.
        ActiveActorDestructible friendlyUnit = createDestructibleActor(100);

        // Add units to lists for collision detection.
        List<ActiveActorDestructible> enemyUnits = new ArrayList<>();
        enemyUnits.add(enemyUnit);

        List<ActiveActorDestructible> friendlyUnits = new ArrayList<>();
        friendlyUnits.add(friendlyUnit);

        // Perform collision handling.
        collisionManager.handleAllCollisions(friendlyUnits, enemyUnits, new ArrayList<>(), new ArrayList<>());

        // Verify that the friendly unit took damage, and the enemy unit was destroyed.
        assertEquals(99, friendlyUnit.getHealth());
        assertTrue(soundsPlayed.contains("TakeDamageAudio"));
        assertTrue(enemyUnit.isDestroyed());
    }

    // Helper method to create a destructible actor with specified health.
    private ActiveActorDestructible createDestructibleActor(int health) {
        return new ActiveActorDestructible(Config.USER_IMAGE, 10, 10, 0, 0, health) {
            @Override
            public void updateActor() {
            }

            @Override
            public void updatePosition() {
            }
        };
    }

    // Helper method to create a projectile with specified damage.
    private ActiveActorDestructible createProjectile(int damage) {
        return new Projectile(Config.ENEMY_MISSILE, 5, 5, 0, 0, 1, damage) {
            @Override
            public void updateActor() {
            }
        };
    }
}
