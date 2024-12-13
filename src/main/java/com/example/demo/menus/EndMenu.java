/**
 * Represents the end menu for the game "F-15: Strike Eagle."
 *
 * The EndMenu class provides options for the user to restart the game,
 * restart the current level, or exit to the main menu after the game ends.
 */
package com.example.demo.menus;

import com.example.demo.managers.StyleManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * EndMenu displays the menu shown after the game ends, offering navigation
 * options for restarting or exiting.
 */
public class EndMenu extends StackPane {

    /**
     * Constructs an EndMenu with options for exiting to the main menu, restarting
     * the game, or restarting the current level.
     *
     * @param screenWidth      the width of the screen.
     * @param screenHeight     the height of the screen.
     * @param onExitToMainMenu the callback to execute when exiting to the main menu.
     * @param onRestartGame    the callback to execute when restarting the game.
     * @param onRestartLevel   the callback to execute when restarting the current level.
     */
    public EndMenu(double screenWidth, double screenHeight, Runnable onExitToMainMenu, Runnable onRestartGame, Runnable onRestartLevel) {
        // Create buttons with associated actions

        Button exitButton = StyleManager.createStyledButton("Exit to Main Menu");
        exitButton.setOnAction(event -> onExitToMainMenu.run());

        Button restartButton = StyleManager.createStyledButton("Restart Game");
        restartButton.setOnAction(event -> onRestartGame.run());

        Button restartLevelButton = StyleManager.createStyledButton("Restart Level");
        restartLevelButton.setOnAction(event -> onRestartLevel.run());

        // Create button layout
        VBox menu = new VBox(20, restartLevelButton, restartButton, exitButton);
        menu.setAlignment(Pos.TOP_CENTER);

        // Spacer for vertical alignment of buttons
        StackPane spacer = new StackPane();
        spacer.setPrefHeight(screenHeight * 0.6); // Pushes buttons downward

        // Root layout combining spacer and menu
        VBox rootLayout = new VBox(spacer, menu);
        rootLayout.setAlignment(Pos.TOP_CENTER);
        this.getChildren().addAll(rootLayout);

        // Set the preferred size of the menu to match the screen size
        this.setPrefSize(screenWidth, screenHeight);
    }
}
