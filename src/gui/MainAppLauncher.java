package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainAppLauncher extends Application {

    // Method that is called from main class to launch the GUI
    public static void begin(String[] args) {
        launch(args);
    }

    // Overriding the start method from Applications class
    public void start(Stage stage) throws Exception {
        // Creates the stage
        stage.setTitle("Library Management System");
        stage.setResizable(true); // Determines if the window can be resized
        // Creating Icons
        Image windowIcon = new Image("bookIcon.png");
        stage.getIcons().add(windowIcon);
        // Adding the watermark
        Image waterMark = new Image("BookLineArt.png");

        // SceneBuilder Scene creation
        Parent rootNode = FXMLLoader.load(getClass().getResource("HomePageScene.fxml"));
        Scene homePageScene = new Scene(rootNode);
        // Setting the stage and showing the home screen
        stage.setScene(homePageScene);
        stage.show();
    }
}