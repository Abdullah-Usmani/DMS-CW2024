package com.example.demo.managers;

import com.example.demo.Config;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class EffectManager {

    public void createSparkEffect(double x, double y, Group root) {
        createEffect(x, y, Config.sparkSize, Config.sparkDuration, Config.sparkImage, root);
    }

    public void createDestructionEffect(double x, double y, Group root) {
        createEffect(x, y, Config.explosionSize, Config.explosionDuration, Config.explosionImage, root);
    }

    private void createEffect(double x, double y, double size, int fadeDuration, String imagePath, Group root) {
        ImageView effectImage = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        effectImage.setFitWidth(size);
        effectImage.setFitHeight(size);
        effectImage.setLayoutX(x);
        effectImage.setLayoutY(y);
        root.getChildren().add(effectImage);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(fadeDuration), effectImage);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> root.getChildren().remove(effectImage));
        fadeOut.play();
    }
}