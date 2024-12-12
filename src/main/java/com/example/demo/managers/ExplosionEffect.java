package com.example.demo.managers;

import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

public class ExplosionEffect {

    private final String imagePath;
    private final double width;
    private final double height;
    private final double fadeDuration;
    private final String soundPath;

    public ExplosionEffect(String imagePath, double width, double height, double fadeDuration, String soundPath) {
        this.imagePath = imagePath;
        this.width = width;
        this.height = height;
        this.fadeDuration = fadeDuration;
        this.soundPath = soundPath;
    }

    public void createEffect(double x, double y, Group root) {
        ImageView explosion = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        explosion.setFitWidth(width);
        explosion.setFitHeight(height);
        explosion.setLayoutX(x);
        explosion.setLayoutY(y);
        root.getChildren().add(explosion);

        playSoundEffect();

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(fadeDuration), explosion);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> root.getChildren().remove(explosion));
        fadeOut.play();
    }

    private void playSoundEffect() {
        if (soundPath != null) {
            AudioClip explosionSound = new AudioClip(getClass().getResource(soundPath).toExternalForm());
            explosionSound.play();
        }
    }
}