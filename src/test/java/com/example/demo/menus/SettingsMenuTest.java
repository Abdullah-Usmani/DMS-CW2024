package com.example.demo.menus;

import com.example.demo.Config;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SettingsMenuTest {

    private Stage stage;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() {
        // Initialize the stage
        Platform.runLater(() -> stage = new Stage());
    }

    @Test
    public void testMenuInitialization() {
        Platform.runLater(() -> {
            SettingsMenu settingsMenu = new SettingsMenu(stage);
            Scene scene = settingsMenu.initializeMenu();
            VBox layout = (VBox) scene.getRoot();

            // Verify menu layout
            assertNotNull(layout, "Menu layout should not be null");
            assertEquals(4, layout.getChildren().size(), "Layout should contain 4 elements");

            // Verify message label
            Label messageLabel = (Label) layout.getChildren().get(0);
            assertNotNull(messageLabel, "Message label should not be null");
            assertFalse(messageLabel.isVisible(), "Message label should be hidden by default");

            // Verify resolution dropdown
            ComboBox<String> resolutionDropdown = (ComboBox<String>) layout.getChildren().get(1);
            assertNotNull(resolutionDropdown, "Resolution dropdown should not be null");
            assertEquals("800x600", resolutionDropdown.getItems().get(0), "Resolution dropdown should contain valid items");

            // Verify apply settings button
            Button applySettingsButton = (Button) layout.getChildren().get(2);
            assertNotNull(applySettingsButton, "Apply Settings button should not be null");
            assertFalse(applySettingsButton.isDisable(), "Apply Settings button should be enabled by default");

            // Verify back button
            Button backButton = (Button) layout.getChildren().get(3);
            assertNotNull(backButton, "Back button should not be null");
        });
    }

    @Test
    public void testApplySettings() {
        Platform.runLater(() -> {
            // Stub Config for testing
            Config.setResolution(800,600);
            Config.setFirstRun(true);

            SettingsMenu settingsMenu = new SettingsMenu(stage);
            Scene scene = settingsMenu.initializeMenu();
            VBox layout = (VBox) scene.getRoot();

            ComboBox<String> resolutionDropdown = (ComboBox<String>) layout.getChildren().get(1);
            Button applySettingsButton = (Button) layout.getChildren().get(2);
            Label messageLabel = (Label) layout.getChildren().get(0);

            // Change resolution and apply settings
            resolutionDropdown.setValue("1024x768");
            applySettingsButton.fire();

            // Verify that settings are applied
            assertEquals(1024, Config.getScreenWidth(), "Screen width should be updated");
            assertEquals(768, Config.getScreenHeight(), "Screen height should be updated");
            assertFalse(Config.isFirstRun(), "First run flag should be set to false");
            assertTrue(resolutionDropdown.isDisable(), "Resolution dropdown should be disabled");
            assertTrue(applySettingsButton.isDisable(), "Apply Settings button should be disabled");
            assertTrue(messageLabel.isVisible(), "Message label should be visible");
        });
    }

    @Test
    public void testApplySettingsWhenFirstRunIsFalse() {
        Platform.runLater(() -> {
            // Stub Config for testing
            Config.setResolution(1280,720);
            Config.setFirstRun(false);

            SettingsMenu settingsMenu = new SettingsMenu(stage);
            Scene scene = settingsMenu.initializeMenu();
            VBox layout = (VBox) scene.getRoot();

            ComboBox<String> resolutionDropdown = (ComboBox<String>) layout.getChildren().get(1);
            Button applySettingsButton = (Button) layout.getChildren().get(2);
            Label messageLabel = (Label) layout.getChildren().get(0);

            // Verify initial state when first run is false
            assertTrue(resolutionDropdown.isDisable(), "Resolution dropdown should be disabled");
            assertTrue(applySettingsButton.isDisable(), "Apply Settings button should be disabled");
            assertTrue(messageLabel.isVisible(), "Message label should be visible");
        });
    }

    @Test
    public void testBackButton() {
        Platform.runLater(() -> {
            SettingsMenu settingsMenu = new SettingsMenu(stage);
            Scene scene = settingsMenu.initializeMenu();
            VBox layout = (VBox) scene.getRoot();

            Button backButton = (Button) layout.getChildren().get(3);
            assertNotNull(backButton, "Back button should not be null");

            // Simulate clicking the back button
            backButton.fire();

            // Verify that the scene is changed to StartMenu
            assertNotNull(stage.getScene(), "Stage scene should not be null after clicking back");
        });
    }
}
