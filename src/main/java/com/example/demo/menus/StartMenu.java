/**
 * Represents the start menu of the game "F-15: Strike Eagle."
 *
 * The StartMenu class initializes and displays the main menu, allowing the user
 * to navigate to various parts of the game such as starting the game, adjusting settings,
 * viewing help, or exiting the application.
 */
package com.example.demo.menus;

import com.example.demo.Config;
import com.example.demo.controller.Controller;
import com.example.demo.managers.StyleManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * StartMenu is responsible for creating the main menu of the game and handling
 * user interactions for navigation to different parts of the application.
 */
public class StartMenu {

    /** The main application stage. */
    private final Stage stage;

    /** The background image displayed on the start menu. */
    private final ImageView background;

    /**
     * Constructs a StartMenu with the specified stage.
     *
     * @param stage the primary stage for the application.
     */
    public StartMenu(Stage stage) {
        this.stage = stage;
        this.background = new ImageView(new Image(getClass().getResource(Config.START_BACKGROUND).toExternalForm()));
    }

    /**
     * Initializes the start menu layout, including buttons for starting the game,
     * opening settings, viewing help, and exiting the application.
     *
     * @return the scene containing the start menu layout.
     */
    public Scene initializeMenu() {
        // Configure the background image
        background.setFitWidth(Config.getScreenWidth());
        background.setFitHeight(Config.getScreenHeight());
        background.setPreserveRatio(false);

        // Create the menu layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // Create the title label
        Label title = StyleManager.createStyledLabel(Config.GAME_TITLE, true, 0.1);

        // Create the "Start Game" button and its event handler
        Button startGameButton = StyleManager.createStyledButton("Start Game");
        startGameButton.setOnAction(e -> {
            Controller controller = new Controller(stage);
            controller.launchGame(); // Launch the game
            Config.setFirstRun(false); // Disable first run flag
        });

        // Create the "Settings" button and its event handler
        Button settingsButton = StyleManager.createStyledButton("Settings");
        settingsButton.setOnAction(e -> {
            SettingsMenu settingsMenu = new SettingsMenu(stage);
            Scene settingsScene = settingsMenu.initializeMenu();
            stage.setScene(settingsScene);
        });

        // Create the "Help" button and its event handler
        Button helpButton = StyleManager.createStyledButton("Help");
        helpButton.setOnAction(e -> {
            HelpMenu helpMenu = new HelpMenu(stage);
            Scene helpScene = helpMenu.initializeMenu();
            stage.setScene(helpScene);
        });

        // Create the "Exit" button and its event handler
        Button exitButton = StyleManager.createStyledButton("Exit");
        exitButton.setOnAction(e -> System.exit(0));

        // Add all elements to the layout
        layout.getChildren().addAll(title, startGameButton, settingsButton, helpButton, exitButton);

        // Combine background and menu layout using StackPane
        StackPane root = new StackPane();
        root.getChildren().addAll(background, layout);

        // Create and return the scene
        return new Scene(root, Config.getScreenWidth(), Config.getScreenHeight());
    }
}
