package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EndGameScreen {
    private final Pane root;
    private final double width;
    private final double height;

    public EndGameScreen(double width, double height, Runnable onRestart, Runnable onExit) {
        this.width = width;
        this.height = height;
        this.root = new Pane(); // Initialize root as a Pane or another layout container

        initializeUI(onRestart, onExit);
    }

    // Initialize UI components
    private void initializeUI(Runnable onRestart, Runnable onExit) {
        // Restart Button
        Button restartButton = new Button("Restart");
        restartButton.setLayoutX(width / 2 - 50); // Center horizontally
        restartButton.setLayoutY(height / 2 - 50); // Positioned near the middle of the screen
        restartButton.setOnAction(event -> onRestart.run());
        root.getChildren().add(restartButton);

        // Exit Button
        Button exitButton = new Button("Exit");
        exitButton.setLayoutX(width / 2 - 50); // Center horizontally
        exitButton.setLayoutY(height / 2 + 20); // Positioned below restart button
        exitButton.setOnAction(event -> onExit.run());
        root.getChildren().add(exitButton);
    }

    public void start(Stage stage) {
        Scene scene = new Scene(root, width, height); // Use the root container
        stage.setScene(scene);
    }

    public Pane getRoot() {
        return root; // Expose the root for external use if needed
    }
}