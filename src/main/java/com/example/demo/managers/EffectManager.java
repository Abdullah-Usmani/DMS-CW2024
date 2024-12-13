/**
 * Manages visual effects for game events in "F-15: Strike Eagle."
 *
 * The EffectManager class handles the creation and management of visual effects such as
 * sparks and explosions, enhancing the game's visual experience.
 */
package com.example.demo.managers;

import com.example.demo.Config;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * EffectManager creates and manages visual effects triggered by game events.
 */
public class EffectManager {

    /**
     * Creates a spark effect at the specified location and adds it to the scene.
     *
     * @param x    the X-coordinate of the effect.
     * @param y    the Y-coordinate of the effect.
     * @param root the root group to which the effect is added.
     */
    public void createSparkEffect(double x, double y, Group root) {
        createEffect(x, y, Config.SPARK_SIZE, Config.SPARK_DURATION, Config.SPARK_IMAGE, root);
    }

    /**
     * Creates a destruction effect (e.g., explosion) at the specified location and adds it to the scene.
     *
     * @param x    the X-coordinate of the effect.
     * @param y    the Y-coordinate of the effect.
     * @param root the root group to which the effect is added.
     */
    public void createDestructionEffect(double x, double y, Group root) {
        createEffect(x, y, Config.EXPLOSION_SIZE, Config.EXPLOSION_DURATION, Config.EXPLOSION_IMAGE, root);
    }

    /**
     * Creates a visual effect using the specified parameters.
     *
     * @param x            the X-coordinate of the effect.
     * @param y            the Y-coordinate of the effect.
     * @param size         the size of the effect.
     * @param fadeDuration the duration of the fade-out animation in seconds.
     * @param imagePath    the path to the image used for the effect.
     * @param root         the root group to which the effect is added.
     */
    private void createEffect(double x, double y, double size, int fadeDuration, String imagePath, Group root) {
        // Load the effect image
        ImageView effectImage = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        effectImage.setFitWidth(size);
        effectImage.setFitHeight(size);
        effectImage.setLayoutX(x);
        effectImage.setLayoutY(y);

        // Add the effect image to the root
        root.getChildren().add(effectImage);

        // Create a fade-out animation for the effect
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(fadeDuration), effectImage);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> root.getChildren().remove(effectImage)); // Remove the effect after fade-out

        // Play the fade-out animation
        fadeOut.play();
    }
}
