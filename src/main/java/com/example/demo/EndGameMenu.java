package com.example.demo;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

public class EndGameMenu extends VBox {

    private final Button exitButton;
    private final Button restartButton;

    public EndGameMenu(String message, double screenWidth, double screenHeight) {
        // Semi-transparent overlay
        Rectangle background = new Rectangle(screenWidth, screenHeight);
        background.setFill(Color.color(0, 0, 0, 0.7)); // Black with 70% opacity

        // End game message
        Text endGameText = new Text(message);
        endGameText.setFont(Font.font(50));
        endGameText.setFill(Color.WHITE);

        // Buttons
        exitButton = new Button("Exit");
        restartButton = new Button("Restart");
        exitButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px;");
        restartButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px;");

        // Layout
        this.getChildren().addAll(endGameText, restartButton, exitButton);
        this.setSpacing(20);
        this.setStyle("-fx-alignment: center;");
        this.setLayoutX((screenWidth - 300) / 2); // Center horizontally
        this.setLayoutY((screenHeight - 200) / 2); // Center vertically
    }

    public Button getExitButton() {
        return exitButton;
    }

    public Button getRestartButton() {
        return restartButton;
    }
}
