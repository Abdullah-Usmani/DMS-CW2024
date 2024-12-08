//package com.example.demo;
//
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class HelpMenu {
//    private final Stage stage;
//
//    public HelpMenu(Stage stage) {
//        this.stage = stage;
//    }
//
//    public void show() {
//        Label helpText = new Label("Controls:\n" +
//                "- UP Arrow: Move Up\n" +
//                "- DOWN Arrow: Move Down\n" +
//                "- SPACE: Fire Projectile\n" +
//                "- M: Fire Missile");
//
//        Button backButton = new Button("Back");
//        backButton.setOnAction(e -> stage.setScene(new StartMenu().startScene(stage))); // Go back to start menu
//
//        VBox layout = new VBox(20);
//        layout.getChildren().addAll(helpText, backButton);
//
//        Scene helpScene = new Scene(layout, 400, 300);
//        stage.setScene(helpScene);
//    }
//}
