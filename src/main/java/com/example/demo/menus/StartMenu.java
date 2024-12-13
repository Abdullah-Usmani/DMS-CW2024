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

public class StartMenu {

    private final Stage stage;
    private final ImageView background;

    public StartMenu(Stage stage) {
        this.stage = stage;
        this.background = new ImageView(new Image(getClass().getResource(Config.startBackground).toExternalForm()));
    }

    public Scene initializeMenu() {
        background.setFitWidth(Config.getScreenWidth());
        background.setFitHeight(Config.getScreenHeight());
        background.setPreserveRatio(false);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // Title Text
        Label title = StyleManager.createStyledLabel(Config.gameTitle, true, 0.1);

        Button startGameButton = StyleManager.createStyledButton("Start Game");
        startGameButton.setOnAction(e -> {
            Controller controller = new Controller(stage);
            controller.launchGame(); // Launch the game
            Config.setFirstRun(false); // Turn on the resolution lock, even if resolution wasn't changed because it would no longer be the first run
        });

        Button settingsButton = StyleManager.createStyledButton("Settings");
        settingsButton.setOnAction(e -> {
            SettingsMenu settingsMenu = new SettingsMenu(stage);
            Scene settingsScene = settingsMenu.initializeMenu();
            stage.setScene(settingsScene);

        });

        Button helpButton = StyleManager.createStyledButton("Help");
        helpButton.setOnAction(e -> {
            HelpMenu helpMenu = new HelpMenu(stage);
            Scene helpScene = helpMenu.initializeMenu();
            stage.setScene(helpScene);
        });

        Button exitButton = StyleManager.createStyledButton("Exit");
        exitButton.setOnAction(e -> System.exit(0));

        layout.getChildren().addAll(title, startGameButton, settingsButton, helpButton, exitButton);

        // Combine background and menu using StackPane
        StackPane root = new StackPane();
        root.getChildren().addAll(background, layout);

        return new Scene(root, Config.getScreenWidth(), Config.getScreenHeight());
    }
}
