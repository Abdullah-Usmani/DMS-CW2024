package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game Menu");

        // Buttons
        Button startButton = new Button("Start Game");
        Button settingsButton = new Button("Settings");
        Button helpButton = new Button("Help");
        Button exitButton = new Button("Exit Game");

        // Button Actions
        startButton.setOnAction(e -> goToLevelOne(primaryStage));
        settingsButton.setOnAction(e -> showSettingsMenu(primaryStage));
        helpButton.setOnAction(e -> showHelpMenu(primaryStage));
        exitButton.setOnAction(e -> System.exit(0));

        // Layout
        VBox layout = new VBox(20);
        layout.getChildren().addAll(startButton, settingsButton, helpButton, exitButton);

        // Scene
        Scene startScene = new Scene(layout, 400, 300);
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    private void goToLevelOne(Stage stage) {
        LevelOne levelOne = new LevelOne();
        Scene levelScene = levelOne.initializeScene();
        stage.setScene(levelScene);
        levelOne.startGame();
    }

    private void showSettingsMenu(Stage stage) {
        SettingsMenu settingsMenu = new SettingsMenu(stage);
        settingsMenu.show();
    }

    private void showHelpMenu(Stage stage) {
        HelpMenu helpMenu = new HelpMenu(stage);
        helpMenu.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
