//package com.example.demo.actors;
//
//import com.example.demo.Config;
//import javafx.scene.image.Image;
//
//public class ProjectileFactory {
//    public static final String IMAGE_LOCATION = "/com/example/demo/images/";
//    private static final int SCREEN_HEIGHT = Config.getScreenHeight();
//    private static final int SCREEN_WIDTH = Config.getScreenWidth();
//   private static final String IMAGE_NAME;
//    private static final Image PLANE_IMAGE = new Image(UserPlane.class.getResource("userfire.png").toExternalForm());
//    private static final int IMAGE_HEIGHT =  (int) (SCREEN_HEIGHT * .01);
//    private static final int src_IMAGE_HEIGHT = (int) PLANE_IMAGE.getHeight(); // Dynamically get height
//    private static final int src_IMAGE_WIDTH = (int) PLANE_IMAGE.getWidth();  // Dynamically get width
//    private static final int IMAGE_WIDTH = (src_IMAGE_WIDTH * IMAGE_HEIGHT) / src_IMAGE_HEIGHT;  // Dynamically get width
//
//    public static Projectile createUserProjectile(double initialXPos, double initialYPos) {
//        return new Projectile(
//                "userfire.png",
//                (int) (SCREEN_HEIGHT * 0.01),
//                (int) (SCREEN_WIDTH * 0.01),
//                initialXPos,
//                initialYPos,
//                SCREEN_WIDTH * 0.01,
//                1 // Normal damage
//        );
//    }
//
//    public static Projectile createEnemyProjectile(double initialXPos, double initialYPos) {
//        return new Projectile(
//                "enemyfire.png",
//                (int) (SCREEN_HEIGHT * 0.03),
//                (int) (SCREEN_WIDTH * 0.03),
//                initialXPos,
//                initialYPos,
//                -(SCREEN_WIDTH * 0.015),
//                1 // Normal damage
//        );
//    }
//
//    public static Projectile createUserMissile(double initialXPos, double initialYPos) {
//        return new Projectile(
//                "usersidewinder.png",
//                (int) (SCREEN_HEIGHT * 0.015),
//                (int) (SCREEN_WIDTH * 0.015),
//                initialXPos,
//                initialYPos,
//                SCREEN_WIDTH * 0.02,
//                3 // 3x damage
//        );
//    }
//
//    public static Projectile createEnemyMissile(double initialXPos, double initialYPos) {
//        return new Projectile(
//                "enemymissiler33.png",
//                (int) (SCREEN_HEIGHT * 0.03),
//                (int) (SCREEN_WIDTH * 0.03),
//                initialXPos,
//                initialYPos,
//                -(SCREEN_WIDTH * 0.02),
//                3 // 3x damage
//        );
//    }
//}