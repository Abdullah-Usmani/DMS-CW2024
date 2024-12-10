package com.example.demo.menus;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EndMenu extends StackPane {

    public EndMenu(double screenWidth, double screenHeight, Runnable onExitToMainMenu, Runnable onRestartLevel) {
        // Buttons
        Button exitButton = createStyledButton("Exit to Main Menu");
        exitButton.setOnAction(event -> onExitToMainMenu.run());

        Button restartButton = createStyledButton("Restart Game");

        Button restartLevelButton = createStyledButton("Restart Level");
        restartLevelButton.setOnAction(event -> onRestartLevel.run());

        // Button layout
        VBox menu = new VBox(20, restartLevelButton, restartButton, exitButton);
        menu.setAlignment(Pos.TOP_CENTER);

        // Spacer for 3/4 positioning
        StackPane spacer = new StackPane();
        spacer.setPrefHeight(screenHeight * 0.6); // Pushes buttons downward (adjust to 0.5-0.75 for exact position)

        // Root layout
        VBox rootLayout = new VBox(spacer, menu);
        rootLayout.setAlignment(Pos.TOP_CENTER); // Ensures buttons remain centered horizontally
        this.getChildren().addAll(rootLayout);

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
}