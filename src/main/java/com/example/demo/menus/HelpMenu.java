/**
 * Represents the help menu for the game "F-15: Strike Eagle."
 *
 * The HelpMenu class displays instructions for the game's controls and allows
 * the user to navigate back to the main menu.
 */
package com.example.demo.menus;

import com.example.demo.Config;
import com.example.demo.managers.StyleManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * HelpMenu provides an interface to display game controls and navigation back to the main menu.
 */
public class HelpMenu {

    /** The main application stage. */
    private final Stage stage;

    /**
     * Constructs a HelpMenu with the specified stage.
     *
     * @param stage the primary stage for the application.
     */
    public HelpMenu(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the help menu layout, displaying game controls and a back button.
     *
     * @return the scene containing the help menu layout.
     */
    public Scene initializeMenu() {
        // Create the layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // Help text label
        Label helpLabel = StyleManager.createStyledLabel(
                """
                    Game Controls:
                    - Arrow Keys: Move Up/Down
                    - Space: Fire Projectile
                    - M: Fire Missile
                    - P: Pause Game
                """,
                false,
                0.1
        );
        helpLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 18px; -fx-text-fill: black;");

        // Back button to return to the main menu
        Button backButton = StyleManager.createStyledButton("Back");
        backButton.setOnAction(e -> goToStartMenu());

        // Add elements to the layout
        layout.getChildren().addAll(helpLabel, backButton);

        // Create and return the scene
        return new Scene(layout, Config.getScreenWidth(), Config.getScreenHeight());
    }

    /**
     * Navigates back to the main menu by initializing the StartMenu class.
     */
    private void goToStartMenu() {
        StartMenu startMenu = new StartMenu(stage);
        Scene menuScene = startMenu.initializeMenu();
        stage.setScene(menuScene);
    }
}
