package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainAppLauncher extends Application {

    // Method that is called from main class to launch the GUI
    public static void begin(String[] args) {
        launch(args);
    }

    // Overriding the start method from Applications class
    @Override
    public void start(Stage stage) throws Exception {
        // Creates the stage
        stage.setTitle("Library Management System");
        stage.setResizable(true); // Determines if the window can be resized
        // Creating title bar icon
        Image windowIcon = new Image("gui/images/bookIcon.png");
        stage.getIcons().add(windowIcon);

        // Close button confirmation
        stage.setOnCloseRequest(event -> {
            event.consume();
            closeWindow(stage);
        });
        // SceneBuilder Scene creation
        Parent rootNode = FXMLLoader.load(getClass().getResource("sceneFxml/HomePageScene.fxml"));
        Scene homePageScene = new Scene(rootNode);
        // Setting the stage and showing the home screen
        stage.setScene(homePageScene);
        stage.show();
    }
    // Function to throw an alert during window closure
    private void closeWindow (Stage stage){
        // Setting the button type to include Yes and No buttons instead of default OK and Cancel
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        // Alert window initialization
        Alert alertWindow = new Alert(Alert.AlertType.CONFIRMATION);
        alertWindow.setTitle("Alert!");
        alertWindow.setHeaderText("Do you want to exit the program?");
        alertWindow.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);

        if(alertWindow.showAndWait().get() == buttonTypeYes){
            System.out.println("Program closed by user");
            stage.close();
        }
    }
}