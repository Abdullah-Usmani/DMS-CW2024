package com.example.demo.menus;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class SettingsMenu {
    private final Stage stage;
    private final Consumer<String> resolutionCallback;

    public SettingsMenu(Stage stage, Consumer<String> resolutionCallback) {
        this.stage = stage;
        this.resolutionCallback = resolutionCallback;
    }

    public void showMenu() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);

        ComboBox<String> resolutionSelector = new ComboBox<>();
        resolutionSelector.getItems().addAll("800x600", "1024x768", "1280x720", "1920x1080");
        resolutionSelector.setValue("800x600"); // Default value

        Button applyButton = new Button("Apply");
        applyButton.setOnAction(e -> {
            String selectedResolution = resolutionSelector.getValue();
            if (selectedResolution != null) {
                resolutionCallback.accept(selectedResolution);
                System.out.println("Selected resolution: " + selectedResolution);
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            StartMenu startMenu = new StartMenu(stage, resolutionCallback);
            startMenu.showMenu();
        });

        layout.getChildren().addAll(resolutionSelector, applyButton, backButton);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}
