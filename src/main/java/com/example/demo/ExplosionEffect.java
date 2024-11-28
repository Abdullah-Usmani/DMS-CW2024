package com.example.demo;

import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

//import java.applet.AudioClip;

public class ExplosionEffect {

    private static final String EXPLOSION_IMAGE_PATH = "/com/example/demo/images/explosion1.png";
    private static final double EXPLOSION_WIDTH = 50;
    private static final double EXPLOSION_HEIGHT = 50;
    private static final double FADE_DURATION_SECONDS = 1.0;

    public static void createExplosion(double x, double y, Group root) {
        // Create the explosion ImageView
        ImageView explosion = new ImageView(new Image(ExplosionEffect.class.getResource(EXPLOSION_IMAGE_PATH).toExternalForm()));
        explosion.setFitWidth(EXPLOSION_WIDTH);
        explosion.setFitHeight(EXPLOSION_HEIGHT);
        explosion.setLayoutX(x);
        explosion.setLayoutY(y);

        // Add the explosion to the scene
        root.getChildren().add(explosion);

        // Create a fade-out animation
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(FADE_DURATION_SECONDS), explosion);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> root.getChildren().remove(explosion)); // Remove explosion after fade-out

        // Play the animation
        fadeOut.play();
    }

//    private static void playExplosionSound() {
//        AudioClip explosionSound = new AudioClip(ExplosionEffect.class.getResource("/com/example/demo/sounds/explosion.wav").toExternalForm());
//        explosionSound.play();
//    }
}