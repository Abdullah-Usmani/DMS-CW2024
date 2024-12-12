package com.example.demo.menus;

import com.example.demo.Config;
import com.example.demo.controller.Main;
import com.example.demo.managers.StyleManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsMenu {
    private final Stage stage;

    public SettingsMenu(Stage stage) {
        this.stage = stage;
    }

    public Scene initializeMenu() {
        // Message for disabled dropdown
        Label messageLabel = new Label();
        messageLabel.setVisible(false); // Initially hidden

        // Resolution dropdown
        ComboBox<String> resolutionDropdown = new ComboBox<>();
        resolutionDropdown.getItems().addAll(
                "800x600",
                "1024x768",
                "1280x720",
                "1920x1080"
        );
        resolutionDropdown.setValue(getCurrentResolution());

        // Apply Settings button
        Button applySettingsButton = StyleManager.createStyledButton("Apply Settings");
        applySettingsButton.setOnAction(event -> {
            String selectedResolution = resolutionDropdown.getValue();
            if (selectedResolution != null) {
                String[] dimensions = selectedResolution.split("x");
                int width = Integer.parseInt(dimensions[0]);
                int height = Integer.parseInt(dimensions[1]);

                // Save resolution and mark as not first run
                Config.setResolution(width, height);
                Config.setFirstRun(false);
                Main.restartGame();

                // Disable resolution changes
                resolutionDropdown.setDisable(true);
                applySettingsButton.setDisable(true);
                messageLabel.setText("Resolution changes can only be made on the first run.");
                messageLabel.setVisible(true);
            }
        });

        // Back to Main Menu button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> goToStartMenu());

        // Check if it's the first run
        if (!Config.isFirstRun()) {
            resolutionDropdown.setDisable(true);
            messageLabel.setText("Resolution changes can only be made on the first run.");
            messageLabel.setVisible(true);
            applySettingsButton.setDisable(true);
        }

        // Layout
        VBox layout = new VBox(20, messageLabel, resolutionDropdown, applySettingsButton, backButton);
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, Config.getScreenWidth(), Config.getScreenHeight());
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
