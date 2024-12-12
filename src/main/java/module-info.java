module com.example.demo {
    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    // Other modules
    requires java.desktop; // If you're using AWT/Swing

    // Opens for reflection (JavaFX)
    opens com.example.demo to javafx.fxml;
    opens com.example.demo.menus to javafx.fxml;
    opens com.example.demo.displays to javafx.fxml;
    opens com.example.demo.levels to javafx.fxml;
    opens com.example.demo.actors to javafx.fxml;

    // Exports for external access
    exports com.example.demo.controller;
    exports com.example.demo;
    opens com.example.demo.managers to javafx.fxml;
}
