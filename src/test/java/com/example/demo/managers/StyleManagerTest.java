package com.example.demo.managers;

import com.example.demo.Config;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StyleManagerTest {

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @Test
    public void testCreateStyledButton() {
        String buttonText = "Click Me!";
        Button button = StyleManager.createStyledButton(buttonText);

        // Verify button properties
        assertEquals(buttonText, button.getText());
        assertNotNull(button.getFont());
        assertEquals(Color.LIMEGREEN, button.getTextFill());

        // Check default style
        String defaultStyle = "-fx-background-color: transparent; -fx-border-color: limegreen; -fx-border-width: 2; " +
                "-fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5;";
        assertEquals(defaultStyle, button.getStyle());

        // Simulate hover effects
        button.getOnMouseEntered().handle(null);
        String hoverStyle = "-fx-background-color: limegreen; -fx-border-color: limegreen; -fx-border-width: 2; " +
                "-fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5; -fx-text-fill: black;";
        assertEquals(hoverStyle, button.getStyle());
        assertTrue(button.getEffect() instanceof DropShadow);

        // Simulate mouse exit
        button.getOnMouseExited().handle(null);
        assertEquals(defaultStyle, button.getStyle());
        assertNull(button.getEffect());
    }

    @Test
    public void testCreateStyledLabel() {
        String labelText = "Game Over!";
        boolean bold = true;
        double sizeFactor = 0.05;

        Label label = StyleManager.createStyledLabel(labelText, bold, sizeFactor);

        // Verify label properties
        assertEquals(labelText, label.getText());
        assertNotNull(label.getFont());
        assertEquals(Color.LIMEGREEN, label.getTextFill());

        // Verify font size
        double expectedFontSize = Config.getScreenHeight() * sizeFactor;
        assertEquals(expectedFontSize, label.getFont().getSize(), 0.1);
    }

    @Test
    public void testCreateStyledDropdown() {
        ComboBox<String> dropdown = StyleManager.createStyledDropdown();

        // Verify dropdown properties
        String expectedStyle = "-fx-background-color: transparent;" +
                "-fx-border-color: limegreen;" +
                "-fx-border-width: 2;" +
                "-fx-padding: 5;" +
                "-fx-text-fill: limegreen;" +
                "-fx-font-family: 'HornetDisplay-Regular';" +
                "-fx-font-size: " + (Config.getScreenHeight() * 0.025) + "px;" +
                "-fx-background-radius: 5;" +
                "-fx-border-radius: 5;";
        assertEquals(expectedStyle, dropdown.getStyle());

        // Simulate hover effects
        dropdown.getOnMouseEntered().handle(null);
        assertTrue(dropdown.getEffect() instanceof DropShadow);

        // Simulate mouse exit
        dropdown.getOnMouseExited().handle(null);
        assertNull(dropdown.getEffect());
    }
}
