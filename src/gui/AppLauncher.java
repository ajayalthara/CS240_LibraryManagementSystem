package gui;

import dataStructures.myHashMap;
import database.DbConnectionWrapper;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AppLauncher extends Application {

    private HomeScene homeScene;

    // Method that is called from main class to launch the GUI
    public static void begin(String[] args) {
        launch(args);
    }

    // Overriding the start method from Applications class
    public void start(Stage stage) throws Exception {
        // Creates the stage
        stage.setTitle("Library Management System");
        stage.setWidth(1000);
        stage.setHeight(700);
        stage.setResizable(true); // Determines if the window can be resized
        // Creating Icons
        Image windowIcon = new Image("bookIcon.png");
        stage.getIcons().add(windowIcon);
        // Adding the watermark
        Image waterMark = new Image("BookLineArt.png");

        // Calling Home scene first
        homeScene = new HomeScene(stage);
        // Setting the stage and showing the home screen
        stage.setScene(homeScene.getScene());
        stage.show();
    }

    public static void setScene(){

    }

    }