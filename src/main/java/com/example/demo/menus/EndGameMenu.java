package com.example.demo.menus;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EndGameMenu extends StackPane {

    private final Button exitButton;
    private final Button restartButton;

    public EndGameMenu(String message, double screenWidth, double screenHeight) {
        // Background
        Rectangle background = new Rectangle(screenWidth, screenHeight);
        background.setFill(Color.color(0, 0, 0, 0.8)); // Grayish-black background

        // End game message
        Text endGameText = new Text(message);
        endGameText.setFont(Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/Roboto-Regular.ttf"), 50));
        endGameText.setFill(Color.CYAN); // Neon blue text

        // Buttons
        exitButton = createStyledButton("Exit");
        restartButton = createStyledButton("Restart");

        // Layout
        VBox menu = new VBox(20, endGameText, restartButton, exitButton);
        menu.setAlignment(Pos.CENTER);

        // Add background and menu to the root
        this.getChildren().addAll(background, menu);
        this.setPrefSize(screenWidth, screenHeight); // Ensure it covers the screen
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/Roboto-Regular.ttf"), 20);
        if (customFont == null) {
            customFont = Font.font("Arial", 20); // Fallback font
        }
        button.setFont(customFont);
        button.setTextFill(Color.CYAN); // Neon blue text
        button.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: cyan; " +
                        "-fx-border-width: 2; " +
                        "-fx-padding: 10; " +
                        "-fx-background-radius: 5; " +
                        "-fx-border-radius: 5;"
        );

        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: cyan; " +
                        "-fx-border-color: cyan; " +
                        "-fx-border-width: 2; " +
                        "-fx-padding: 10; " +
                        "-fx-background-radius: 5; " +
                        "-fx-border-radius: 5;" +
                        "-fx-text-fill: black;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: cyan; " +
                        "-fx-border-width: 2; " +
                        "-fx-padding: 10; " +
                        "-fx-background-radius: 5; " +
                        "-fx-border-radius: 5;"
        ));

        button.setPrefWidth(200); // Ensure all buttons are the same width
        return button;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public Button getRestartButton() {
        return restartButton;
    }
}
