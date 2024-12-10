package com.example.demo.menus;

import com.example.demo.Config;
import com.example.demo.controller.Main;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class SettingsMenu {
    private final Stage stage;

    public SettingsMenu(Stage stage) {
        this.stage = stage;
    }

    public Scene initializeMenu() {
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
        Button applySettingsButton = new Button("Apply Settings");
        applySettingsButton.setOnAction(event -> {
            String selectedResolution = resolutionDropdown.getValue();
            if (selectedResolution != null) {
                String[] dimensions = selectedResolution.split("x");
                int width = Integer.parseInt(dimensions[0]);
                int height = Integer.parseInt(dimensions[1]);

                // Confirm restart for resolution change
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle("Confirm Restart");
                confirmationDialog.setHeaderText("Restart Required");
                confirmationDialog.setContentText("Do you want to restart the game to apply the new resolution?");
                Optional<ButtonType> result = confirmationDialog.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Save resolution in Config
                    Config.setResolution(width, height);

                    // Restart the game
                    Main.restartGame();
                }
            }
        });

        // Back to Main Menu button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> goToStartMenu());

        // Layout
        VBox layout = new VBox(20, resolutionDropdown, applySettingsButton, backButton);
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, Config.getScreenWidth(), Config.getScreenHeight());
    }

    private String getCurrentResolution() {
        return Config.getScreenWidth() + "x" + Config.getScreenHeight();
    }

    private void goToStartMenu() {
        // Load StartMenu without resizing the stage
        StartMenu startMenu = new StartMenu(stage);
        Scene menuScene = startMenu.initializeMenu();
        stage.setScene(menuScene);
    }
}
