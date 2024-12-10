package com.example.demo.menus;

import com.example.demo.Config;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsMenu {

    private final Stage stage;

    public SettingsMenu(Stage stage) {
        this.stage = stage;
    }

    public Scene initializeMenu() {
        // Create resolution dropdown
        ComboBox<String> resolutionDropdown = new ComboBox<>();
        resolutionDropdown.getItems().addAll("800x600", "1024x768", "1280x720", "1920x1080");
        resolutionDropdown.setValue(getCurrentResolution());

        // Apply resolution change
        Button applyButton = new Button("Apply");
        applyButton.setOnAction(e -> {
            String selectedResolution = resolutionDropdown.getValue();
            applyResolution(selectedResolution);
        });

        // Back to start menu
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> goToStartMenu());

        // Arrange controls in a vertical layout
        VBox layout = new VBox(20, resolutionDropdown, applyButton, backButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        return new Scene(layout, Config.getScreenWidth(), Config.getScreenHeight());
    }

    private void applyResolution(String resolution) {
        String[] dimensions = resolution.split("x");
        Config.setScreenWidth(Integer.parseInt(dimensions[0]));
        Config.setScreenHeight(Integer.parseInt(dimensions[1]));
        stage.setWidth(Config.getScreenWidth());
        stage.setHeight(Config.getScreenHeight());
        stage.centerOnScreen();
    }

    private String getCurrentResolution() {
        return Config.getScreenWidth() + "x" + Config.getScreenHeight();
    }

    private void goToStartMenu() {
        StartMenu startMenu = new StartMenu(stage);
        Scene menuScene = startMenu.initializeMenu();
        stage.setScene(menuScene);
    }
}