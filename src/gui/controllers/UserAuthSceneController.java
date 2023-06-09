package gui.controllers;


import dataStructures.myHashMap;
import dataStructures.myLinkedList;
import database.DbConnectionWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthSceneController {
    // Creates the hash table and the linked list
    private myHashMap<String, String> bookTable = new myHashMap<>();
    private myLinkedList bookList = new myLinkedList();
    public void setBookTable(myHashMap bookTable) {
        this.bookTable = bookTable;
    }
    public void setBookList(myLinkedList bookList) {
        this.bookList = bookList;
    }
    private Stage stage;
    private Scene scene;
    private Parent rootNode;
    @FXML
    private TextField userIdText;
    @FXML
    private TextField passwordText;
    String userActive, userRole;
    public void userLogin(ActionEvent event) throws IOException {
        // Taking all the text field values in the screen into strings
        // The substring function is called to limit the number of characters to what is allowed in DB tables
        String userId = userIdText.getText();
        String password = passwordText.getText();
        // Checking for the correct user-id password combination
        DbConnectionWrapper dbWrapper = new DbConnectionWrapper();
        try {
            dbWrapper.connect();
            ResultSet userDetails = dbWrapper.executeQueryWithParam("Select active, role FROM user_details WHERE user_id = ? and password = ?", userId, password);
            // Load home page on successful login
            if (userDetails.wasNull()){
                dbWrapper.disconnect();
                // Alert showing error if login unsuccessful
                Alert addSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                addSuccessAlert.setTitle("User Login Failed");
                addSuccessAlert.setHeaderText("UserID or Password is incorrect. Try again!!");
                addSuccessAlert.showAndWait();
                // Clearing the text fields
                userIdText.clear();
                passwordText.clear();
            } else {
                while (userDetails.last()){
                    userActive = userDetails.getString(1);
                    userRole = userDetails.getString(2);
                }
                dbWrapper.disconnect();
                if (userActive.equals("Y")){
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
            }
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
