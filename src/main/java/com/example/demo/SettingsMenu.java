//package com.example.demo;
//
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class SettingsMenu extends Application {
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Game Menu");
//
//        // Buttons
//        Button startButton = new Button("Start Game");
//        Button settingsButton = new Button("Settings");
//        Button helpButton = new Button("Help");
//        Button exitButton = new Button("Exit Game");
//
//        // Button Actions
//        startButton.setOnAction(e -> goToLevelOne(primaryStage));
//        settingsButton.setOnAction(e -> showSettingsMenu(primaryStage));
//        helpButton.setOnAction(e -> showHelpMenu(primaryStage));
//        exitButton.setOnAction(e -> System.exit(0));
//
//        // Layout
//        VBox layout = new VBox(20);
//        layout.getChildren().addAll(startButton, settingsButton, helpButton, exitButton);
//
//        // Scene
//        Scene startScene = new Scene(layout, 400, 300);
//        primaryStage.setScene(startScene);
//        primaryStage.show();
//    }
//
//    private void goToLevelOne(Stage stage) {
//        LevelOne levelOne = new LevelOne();
//        Scene levelScene = levelOne.initializeScene();
//        stage.setScene(levelScene);
//        levelOne.startGame();
//    }
//
//    private void showSettingsMenu(Stage stage) {
//        VBox settingsLayout = new VBox(20);
//
//        Button resolutionButton = new Button("Set Resolution");
//        Button backButton = new Button("Back");
//
//        resolutionButton.setOnAction(e -> {
//            // Handle resolution change logic here
//            System.out.println("Resolution changed!");
//        });
//
//        backButton.setOnAction(e -> start(stage)); // Return to main menu
//
//        settingsLayout.getChildren().addAll(resolutionButton, backButton);
//        Scene settingsScene = new Scene(settingsLayout, 400, 300);
//        stage.setScene(settingsScene);
//    }
//
//    private void showHelpMenu(Stage stage) {
//        VBox helpLayout = new VBox(20);
//
//        Button backButton = new Button("Back");
//        backButton.setOnAction(e -> start(stage)); // Return to main menu
//
//        helpLayout.getChildren().addAll(
//                new javafx.scene.control.Label("Controls:"),
//                new javafx.scene.control.Label("Arrow Keys: Move"),
//                new javafx.scene.control.Label("Space: Fire"),
//                new javafx.scene.control.Label("M: Fire Missile"),
//                backButton
//        );
//
//        Scene helpScene = new Scene(helpLayout, 400, 300);
//        stage.setScene(helpScene);
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
