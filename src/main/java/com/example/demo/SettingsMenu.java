package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsMenu {
    private final Stage stage;

    public SettingsMenu(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        ChoiceBox<String> resolutionChoiceBox = new ChoiceBox<>();
        resolutionChoiceBox.getItems().addAll("800x600", "1024x768", "1366x768", "1280x720", "1920x1080");
        resolutionChoiceBox.setValue("1024x768");

        Button applyButton = new Button("Apply");
        applyButton.setOnAction(e -> {
            String resolution = resolutionChoiceBox.getValue();
            applyResolution(resolution);
            stage.setScene(new StartMenu().startScene(stage)); // Go back to start menu
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(resolutionChoiceBox, applyButton);

        Scene settingsScene = new Scene(layout, 400, 300);
        stage.setScene(settingsScene);
    }

    private void applyResolution(String resolution) {
        String[] dimensions = resolution.split("x");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        stage.setWidth(width);
        stage.setHeight(height);
    }
}
