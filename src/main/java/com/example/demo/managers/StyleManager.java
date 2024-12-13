package com.example.demo.managers;

import com.example.demo.Config;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StyleManager {
    // Centralized button creation
    public static Button createStyledButton(String text) {
        Button button = new Button(text);

        // Load custom font
        Font customFont = Font.loadFont(StyleManager.class.getResourceAsStream("/com/example/demo/fonts/HornetDisplay-Regular.ttf"), Config.getScreenHeight() * 0.03);
        button.setFont(customFont);

        // Set button colors and styles
        button.setTextFill(Color.LIMEGREEN);
        button.setStyle("-fx-background-color: transparent; -fx-border-color: limegreen; -fx-border-width: 2; -fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5;");

        // Add hover effect with shadow
        DropShadow hoverShadow = new DropShadow();
        hoverShadow.setColor(Color.YELLOW);
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

    // Centralized label creation
    public static Label createStyledLabel(String text, boolean bold, double sizeFactor) {
        Label label = new Label(text);
        Font font = Font.loadFont(
                StyleManager.class.getResourceAsStream(bold
                        ? "/com/example/demo/fonts/HornetDisplay-Bold.ttf"
                        : "/com/example/demo/fonts/HornetDisplay-Regular.ttf"),
                Config.getScreenHeight() * sizeFactor
        );
        label.setFont(font);
        label.setTextFill(Color.LIMEGREEN);
        return label;
    }

    public static <T> ComboBox<T> createStyledDropdown() {
        ComboBox<T> dropdown = new ComboBox<>();

        // Font
        Font customFont = Font.loadFont(
                StyleManager.class.getResourceAsStream("/com/example/demo/fonts/HornetDisplay-Regular.ttf"),
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
        hoverShadow.setRadius(20);
        hoverShadow.setSpread(0.5);

        dropdown.setOnMouseEntered(e -> dropdown.setEffect(hoverShadow));
        dropdown.setOnMouseExited(e -> dropdown.setEffect(null));

        return dropdown;
    }
}
