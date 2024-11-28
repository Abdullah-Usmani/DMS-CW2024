package com.example.demo;

import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.media.AudioClip;
import javafx.util.Duration;

public class ExplosionEffect {

    private String imagePath;
    private double width;
    private double height;
    private double fadeDuration;
    private String soundPath; // Path to the sound effect

    public ExplosionEffect(String imagePath, double width, double height, double fadeDuration, String soundPath) {
        this.imagePath = imagePath;
        this.width = width;
        this.height = height;
        this.fadeDuration = fadeDuration;
        this.soundPath = soundPath;
    }

    public void createEffect(double x, double y, Group root) {
        // Create the explosion ImageView
        ImageView explosion = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        explosion.setFitWidth(width);
        explosion.setFitHeight(height);
        explosion.setLayoutX(x);
        explosion.setLayoutY(y);

        // Add the explosion to the scene
        root.getChildren().add(explosion);

        // Play the sound effect
//        playSoundEffect();

        // Create a fade-out animation
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(fadeDuration), explosion);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> root.getChildren().remove(explosion)); // Remove explosion after fade-out

        // Play the animation
        fadeOut.play();
    }

//    private void playSoundEffect() {
//        if (soundPath != null) {
//            AudioClip explosionSound = new AudioClip(getClass().getResource(soundPath).toExternalForm());
//            explosionSound.play();
//        }
//    }
}