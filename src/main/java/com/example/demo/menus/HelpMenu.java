package com.example.demo.menus;

import com.example.demo.Config;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelpMenu {

    private final Stage stage;

    public HelpMenu(Stage stage) {
        this.stage = stage;
    }

    public Scene initializeMenu() {

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Label helpLabel = new Label("""
                Game Controls:
                - Arrow Keys: Move Up/Down
                - Space: Fire Projectile
                - M: Fire Missile
                - P: Pause Game""");
        helpLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 18px; -fx-text-fill: black;");

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> goToStartMenu());

        layout.getChildren().addAll(helpLabel, backButton);

        return new Scene(layout, Config.getScreenWidth(), Config.getScreenHeight());
    }

    private void goToStartMenu() {
        StartMenu startMenu = new StartMenu(stage);
        Scene menuScene = startMenu.initializeMenu();
        stage.setScene(menuScene);
    }
}
