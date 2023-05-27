package gui;

import dataStructures.myHashMap;
import dataStructures.myLinkedList;
import database.DbConnectionWrapper;
import gui.controllers.HomeSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainAppLauncher extends Application {
    // Creates the hash table and the linked list
    myHashMap bookTable = new myHashMap();
    myLinkedList bookList = new myLinkedList();

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

        // Loading all book records from DB into HashTable
        loadDataFromDBToTable();

        // SceneBuilder Scene creation
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sceneFxml/HomePageScene.fxml"));
        Parent rootNode = loader.load();

        //Setting a common object for the hash table and linked list.
        //This will be passed between scene controllers
        HomeSceneController homeSceneController = loader.getController();
        homeSceneController.setBookList(bookList);
        homeSceneController.setBookTable(bookTable);

        //Parent rootNode = FXMLLoader.load(getClass().getResource("sceneFxml/HomePageScene.fxml"));
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
            if(!(bookList.isListEmpty())) {
                // Making DB connection
                DbConnectionWrapper dbWrapper = new DbConnectionWrapper();
                try {
                    dbWrapper.connect();
                    ArrayList<String> dbEntriesBeforeSplit = bookList.getAllNodeDataInArray();
                    for (String element : dbEntriesBeforeSplit){
                        String title =  element.split("\\|")[0];
                        String author = element.split("\\|")[1];
                        String genre = element.split("\\|")[2];
                        // Posting each book record in DB
                        int rowsAffected = dbWrapper.executeUpdate("INSERT INTO book_details (title, author, genre) values (?,?,?)",title,author,genre);
                    }
                    dbWrapper.disconnect();
                    // Alert showing successful addition of record
                    Alert addSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                    addSuccessAlert.setTitle("Data Saved");
                    addSuccessAlert.setHeaderText("Unsaved records transferred to DB");
                    addSuccessAlert.showAndWait();
                    //Clearing the local data list
                    bookList.clear();
                } catch (SQLException ex) {
                    // DB connection error alert
                    Alert addFailureAlert = new Alert(Alert.AlertType.INFORMATION);
                    addFailureAlert.setTitle("Failed");
                    addFailureAlert.setHeaderText("DB Error. Check connection parameters");
                    addFailureAlert.showAndWait();
                    //Clearing the local data list
                    bookList.clear();
                    throw new RuntimeException(ex);
                }
                stage.close();
            } else{
                System.out.println("Program closed by user");
                stage.close();
            }
        }
    }
    // This function loads the contents of book_details table into a Hashmap at the start of the program
    private void loadDataFromDBToTable(){
        // Making DB connection
        DbConnectionWrapper dbWrapper = new DbConnectionWrapper();
        try {
            dbWrapper.connect();
            ResultSet bookDetails = dbWrapper.executeQuery("Select Title, Author from Book_Details");
            while (bookDetails.next()){
                String title = bookDetails.getString(1);
                String author = bookDetails.getString(2);
                bookTable.put(title,author);
            }
            //System.out.println(bookTable);
            bookDetails.close();
            dbWrapper.disconnect();
        }
        catch (SQLException ex) {
            // DB connection error alert
            Alert addFailureAlert = new Alert(Alert.AlertType.INFORMATION);
            addFailureAlert.setTitle("Failed");
            addFailureAlert.setHeaderText("DB Error. Check connection parameters");
            addFailureAlert.showAndWait();
            //Clearing the local data list
            bookList.clear();
            throw new RuntimeException(ex);
        }
    }
}