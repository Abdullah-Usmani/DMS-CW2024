package com.example.demo.menus;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.function.Consumer;

public class HelpMenu {
    private final Stage stage;
    private final Consumer<String> resolutionCallback;

    public HelpMenu(Stage stage, Consumer<String> resolutionCallback) {
        this.stage = stage;
        this.resolutionCallback = resolutionCallback;
    }

    public void showMenu() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Label helpLabel = new Label("Game Controls:\n" +
                "- Arrow Keys: Move Up/Down\n" +
                "- Space: Fire Projectile\n" +
                "- M: Fire Missile\n" +
                "- P: Pause Game");
        helpLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 18px; -fx-text-fill: black;");

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> backToStartMenu());

        layout.getChildren().addAll(helpLabel, backButton);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void backToStartMenu() {
        StartMenu startMenu = new StartMenu(stage, resolutionCallback);
        startMenu.showMenu();
    }
}
