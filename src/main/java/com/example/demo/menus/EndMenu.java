package com.example.demo.menus;

import com.example.demo.managers.StyleManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EndMenu extends StackPane {

    public EndMenu(double screenWidth, double screenHeight, Runnable onExitToMainMenu, Runnable onRestartGame, Runnable onRestartLevel) {
        // Buttons

        Button exitButton = StyleManager.createStyledButton("Exit to Main Menu");
        exitButton.setOnAction(event -> onExitToMainMenu.run());

        Button restartButton = StyleManager.createStyledButton("Restart Game");
        restartButton.setOnAction(event -> onRestartGame.run());

        Button restartLevelButton = StyleManager.createStyledButton("Restart Level");
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
}