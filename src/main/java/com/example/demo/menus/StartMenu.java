package com.example.demo.menus;

import com.example.demo.controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.function.Consumer;

public class StartMenu {
    private final Stage stage;
    private final Consumer<String> resolutionCallback;

    public StartMenu(Stage stage, Consumer<String> resolutionCallback) {
        this.stage = stage;
        this.resolutionCallback = resolutionCallback;
    }

    public void showMenu() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> startGame());

        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(e -> showSettingsMenu());

        Button helpButton = new Button("Help");
        helpButton.setOnAction(e -> showHelpMenu());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> exitGame());

        layout.getChildren().addAll(startButton, settingsButton, helpButton, exitButton);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void startGame() {
        System.out.println("Starting the game...");
        Controller controller = new Controller(stage);
        controller.launchGame();
    }

    private void showSettingsMenu() {
        SettingsMenu settingsMenu = new SettingsMenu(stage, resolutionCallback);
        settingsMenu.showMenu();
    }

    private void showHelpMenu() {
        HelpMenu helpMenu = new HelpMenu(stage, resolutionCallback);
        helpMenu.showMenu();
    }

    private void exitGame() {
        System.out.println("Exiting the game...");
        System.exit(0);
    }
}
