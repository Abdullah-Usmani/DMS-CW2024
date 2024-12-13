package com.example.demo.displays;

import com.example.demo.Config;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class BossHealthDisplay {

    private static final String heartImage_NAME = Config.heartImage;
    private static final double HEART_SIZE = Config.heartSize;
    private static final double SPACING = Config.heartSpacing;

    private final HBox container;
    private final Label healthCounter;
    private int health;

    public BossHealthDisplay(double xPosition, double yPosition, int initialHealth) {
        this.health = initialHealth;
        this.container = new HBox(SPACING);

        container.setLayoutX(xPosition);
        container.setLayoutY(yPosition);

        // Add heart image
        ImageView heartImage = new ImageView(new Image(getClass().getResource(heartImage_NAME).toExternalForm()));
        heartImage.setFitHeight(HEART_SIZE);
        heartImage.setFitWidth(HEART_SIZE);
        container.getChildren().add(heartImage);

        // Add health counter label
        this.healthCounter = new Label("x " + health);
        healthCounter.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        container.getChildren().add(healthCounter);
    }

    public void updateHealth(int newHealth) {
        this.health = newHealth;
        healthCounter.setText("x " + health);
    }

    public HBox getContainer() {
        return container;
    }
}
