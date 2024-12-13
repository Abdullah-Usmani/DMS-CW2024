/**
 * Provides centralized styling utilities for the game's user interface in "F-15: Strike Eagle."
 *
 * The StyleManager class offers methods to create styled buttons, labels, and dropdown menus,
 * ensuring a consistent look and feel throughout the game's UI.
 */
package com.example.demo.managers;

import com.example.demo.Config;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * StyleManager contains methods for creating styled UI components.
 */
public class StyleManager {

    /**
     * Private constructor to prevent instantiation of the utility class.
     */
    private StyleManager() {
        throw new IllegalStateException("StyleManager class");
    }

    /**
     * Creates a styled button with a custom font and hover effects.
     *
     * @param text the text to display on the button.
     * @return a styled Button object.
     */
    public static Button createStyledButton(String text) {
        Button button = new Button(text);

        // Load custom font
        Font customFont = Font.loadFont(StyleManager.class.getResourceAsStream(Config.FONT_REGULAR), Config.getScreenHeight() * 0.03);
        button.setFont(customFont);

        // Set button colors and styles
        button.setTextFill(Color.LIMEGREEN);
        button.setStyle("-fx-background-color: transparent; -fx-border-color: limegreen; -fx-border-width: 2; -fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5;");

        // Add hover effect with shadow
        DropShadow hoverShadow = new DropShadow();
        hoverShadow.setColor(Color.BLACK);
        hoverShadow.setRadius(10);
        hoverShadow.setSpread(0.5);

        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: limegreen; -fx-border-color: limegreen; -fx-border-width: 2; -fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5; -fx-text-fill: black;");
            button.setEffect(hoverShadow);
        });

        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: transparent; -fx-border-color: limegreen; -fx-border-width: 2; -fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5;");
            button.setEffect(null);
        });

        button.setPrefWidth(Config.getScreenWidth() * 0.3); // Dynamic width
        return button;
    }

    /**
     * Creates a styled label with custom font and text color.
     *
     * @param text      the text to display on the label.
     * @param bold      whether the label should use a bold font.
     * @param sizeFactor a factor to determine the font size relative to screen height.
     * @return a styled Label object.
     */
    public static Label createStyledLabel(String text, boolean bold, double sizeFactor) {
        Label label = new Label(text);
        Font font = Font.loadFont(
                StyleManager.class.getResourceAsStream(bold
                        ? Config.FONT_BOLD
                        : Config.FONT_REGULAR),
                Config.getScreenHeight() * sizeFactor
        );
        label.setFont(font);
        label.setTextFill(Color.LIMEGREEN);
        return label;
    }

    /**
     * Creates a styled dropdown menu with custom font and hover effects.
     *
     * @param <T> the type of items in the dropdown menu.
     * @return a styled ComboBox object.
     */
    public static <T> ComboBox<T> createStyledDropdown() {
        ComboBox<T> dropdown = new ComboBox<>();

        // Font
        Font customFont = Font.loadFont(
                StyleManager.class.getResourceAsStream(Config.FONT_REGULAR),
                Config.getScreenHeight() * 0.025
        );
        dropdown.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: limegreen;" +
                        "-fx-border-width: 2;" +
                        "-fx-padding: 5;" +
                        "-fx-text-fill: limegreen;" +
                        "-fx-font-family: 'HornetDisplay-Regular';" +
                        "-fx-font-size: " + (Config.getScreenHeight() * 0.025) + "px;" +
                        "-fx-background-radius: 5;" +
                        "-fx-border-radius: 5;"
        );

        // Hover effects
        DropShadow hoverShadow = new DropShadow();
        hoverShadow.setColor(Color.BLACK);
        hoverShadow.setRadius(10);
        hoverShadow.setSpread(0.5);

        dropdown.setOnMouseEntered(e -> dropdown.setEffect(hoverShadow));
        dropdown.setOnMouseExited(e -> dropdown.setEffect(null));

        return dropdown;
    }
}
