package com.example.demo;

import com.example.demo.controller.Main;

public class ProjectileFactory {
    private static final int SCREEN_HEIGHT = Main.getScreenHeight();
    private static final int SCREEN_WIDTH = Main.getScreenWidth();

    public static Projectile createUserProjectile(double initialXPos, double initialYPos) {
        return new Projectile(
                "userfire.png",
                (int) (SCREEN_HEIGHT * 0.01),
                initialXPos,
                initialYPos,
                SCREEN_WIDTH * 0.01,
                1 // Normal damage
        );
    }

    public static Projectile createEnemyProjectile(double initialXPos, double initialYPos) {
        return new Projectile(
                "enemyFire.png",
                (int) (SCREEN_HEIGHT * 0.03),
                initialXPos,
                initialYPos,
                -(SCREEN_WIDTH * 0.015),
                1 // Normal damage
        );
    }

    public static Projectile createUserMissile(double initialXPos, double initialYPos) {
        return new Projectile(
                "sidewinder.png",
                (int) (SCREEN_HEIGHT * 0.015),
                initialXPos,
                initialYPos,
                SCREEN_WIDTH * 0.02,
                3 // 3x damage
        );
    }

    public static Projectile createEnemyMissile(double initialXPos, double initialYPos) {
        return new Projectile(
                "enemymissiler33.png",
                (int) (SCREEN_HEIGHT * 0.03),
                initialXPos,
                initialYPos,
                -(SCREEN_WIDTH * 0.02),
                3 // 3x damage
        );
    }
}