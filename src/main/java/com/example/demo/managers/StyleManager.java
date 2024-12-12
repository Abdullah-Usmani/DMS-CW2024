package com.example.demo.managers;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StyleManager {
    public static Button createStyledButton(String text) {
        Button button = new Button(text);
        Font customFont = Font.loadFont(StyleManager.class.getResourceAsStream("/com/example/demo/fonts/Roboto-Regular.ttf"), 20);
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
}
