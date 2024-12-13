/**
 * Represents the settings menu for the game "F-15: Strike Eagle."
 *
 * The SettingsMenu class allows users to configure game settings, such as screen resolution,
 * during the initial run of the game. It also provides navigation back to the main menu.
 */
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

/**
 * SettingsMenu handles the user interface for modifying game settings
 * and navigation to other parts of the application.
 */
public class SettingsMenu {

    /** The main application stage. */
    private final Stage stage;

    /**
     * Constructs a SettingsMenu with the specified stage.
     *
     * @param stage the primary stage for the application.
     */
    public SettingsMenu(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the settings menu layout, allowing users to change the resolution
     * (only on the first run) and navigate back to the main menu.
     *
     * @return the scene containing the settings menu layout.
     */
    public Scene initializeMenu() {
        // Message for disabled resolution changes
        Label messageLabel = StyleManager.createStyledLabel(
                "Resolution changes can only be made on the first run.",
                false,
                0.05
        );
        messageLabel.setVisible(false); // Initially hidden

        // Resolution dropdown
        ComboBox<String> resolutionDropdown = StyleManager.createStyledDropdown();
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
                messageLabel.setVisible(true);
            }
        });

        // Back to Main Menu button
        Button backButton = StyleManager.createStyledButton("Back");
        backButton.setOnAction(e -> goToStartMenu());

        // Check if resolution changes are allowed
        if (!Config.isFirstRun()) {
            resolutionDropdown.setDisable(true);
            messageLabel.setVisible(true);
            applySettingsButton.setDisable(true);
        }

        // Layout setup
        VBox layout = new VBox(20, messageLabel, resolutionDropdown, applySettingsButton, backButton);
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, Config.getScreenWidth(), Config.getScreenHeight());
    }

    /**
     * Retrieves the current resolution as a string in the format "widthxheight."
     *
     * @return the current screen resolution.
     */
    private String getCurrentResolution() {
        return Config.getScreenWidth() + "x" + Config.getScreenHeight();
    }

    /**
     * Navigates back to the main menu by initializing the StartMenu class.
     */
    private void goToStartMenu() {
        StartMenu startMenu = new StartMenu(stage);
        Scene menuScene = startMenu.initializeMenu();
        stage.setScene(menuScene);
    }
}
