package com.example.demo.menus;

import com.example.demo.Config;
import com.example.demo.controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartMenu {

    private final Stage stage;

    public StartMenu(Stage stage) {
        this.stage = stage;
    }

    public Scene initializeMenu() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(e -> {
            Controller controller = new Controller(stage);
            controller.launchGame(); // Launch the game
            Config.setFirstRun(false); // Turn on the resolution lock, even if resolution wasn't changed because it would no longer be the first run
        });

        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(e -> {
            SettingsMenu settingsMenu = new SettingsMenu(stage);
            Scene settingsScene = settingsMenu.initializeMenu();
            stage.setScene(settingsScene);

        });

        Button helpButton = new Button("Help");
        helpButton.setOnAction(e -> {
            HelpMenu helpMenu = new HelpMenu(stage);
            Scene helpScene = helpMenu.initializeMenu();
            stage.setScene(helpScene);
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> System.exit(0));

        layout.getChildren().addAll(startGameButton, settingsButton, helpButton, exitButton);

        return new Scene(layout, Config.getScreenWidth(), Config.getScreenHeight());
    }
}
