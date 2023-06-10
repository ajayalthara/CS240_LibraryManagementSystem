package gui.controllers;


import dataStructures.myHashMap;
import dataStructures.myLinkedList;
import database.DbConnectionWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
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
    private PasswordField passwordText;
    private ActionEvent event;
    String userActive, userRole;

    public void userLogin(ActionEvent event){
        this.event = event;
        String userId = userIdText.getText();
        String password = passwordText.getText();
        if(userId.length() == 0 || password.length() ==0){
            makeAlertTitleHeader("Failed!", "one or more text fields are empty, please try again.");
        }else{
            getUserData(userId, password);
        }
    }

    private void getUserData(String userId, String password){
            ResultSet userDetails = getUserDetailsFromDb(userId, password);
            getUserActiveRoleFrom(userDetails);
            checkIfUserActiveAndLoadHome();
    }
    private void getUserActiveRoleFrom(ResultSet userDetails){
        if(userDetailsIsNull(userDetails)) {
            failedAuthenticationAlert();
        }else{
            getUserActiveRoleFromLastRow(userDetails);
        }
    }
    private ResultSet getUserDetailsFromDb(String userId, String password){
        ResultSet userDetails = null;
        DbConnectionWrapper dbWrapper = new DbConnectionWrapper();
        try{
            dbWrapper.connect();
            userDetails = dbWrapper.executeQueryWithParam("Select active, role FROM user_details WHERE user_id = ? and password = ?", userId, password);
        }catch(SQLException ex){
            sqlConnectionAlert();
        }

        dbWrapperDisconnect(dbWrapper);
        return userDetails;
    }
    private boolean userDetailsIsNull(ResultSet userDetails){
        try{
            if(userDetails.wasNull()) return true;
        }catch(SQLException ex){
            sqlConnectionAlert();
        }
        return false;
    }
    private void getUserActiveRoleFromLastRow(ResultSet userDetails){
        try{
            if (userDetails.next()){ //this causes the error
                userActive = userDetails.getString(1);
                userRole = userDetails.getString(2);
            }
        }catch(SQLException ex){
            sqlConnectionAlert();
        }
    }
    private void checkIfUserActiveAndLoadHome(){
        if (userActive != null && userActive.equals("Y")){
            loadHomePageScene();
        }else{
            failedAuthenticationAlert();
        }
    }
    private void loadHomePageScene(){
        try {
            loadDataFromDBToTable();
            // SceneBuilder Scene creation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneFxml/HomePageScene.fxml"));
            Parent rootNode = loader.load();
            //Setting a common object for the hash table and linked list.
            //This will be passed between scene controllers
            HomeSceneController homeSceneController = loader.getController();
            homeSceneController.setBookList(bookList);
            homeSceneController.setBookTable(bookTable);
            setHomeSceneController(loader);
            setSceneAndShowFrom(rootNode);
        }catch (IOException ex){
            failedLoaderAlert();
        }
    }

    private void setHomeSceneController(FXMLLoader loader){
        //Setting a common object for the hash table and linked list.
        //This will be passed between scene controllers
        HomeSceneController homeSceneController = loader.getController();
        homeSceneController.setBookList(bookList);
        homeSceneController.setBookTable(bookTable);
    }

    private void setSceneAndShowFrom(Parent rootNode){
        Scene homePageScene = new Scene(rootNode);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Setting the stage and showing the home screen
        stage.setScene(homePageScene);
        stage.show();
    }

    private void dbWrapperDisconnect(DbConnectionWrapper dbWrapper){
        try{
            dbWrapper.disconnect();
        }catch(SQLException ex){
            sqlConnectionAlert();
        }
    }

    private void sqlConnectionAlert(){
        // DB connection error alert
        makeAlertTitleHeader("Failed", "DB Error. Check connection parameters");
        //Clearing the local data list
        bookList.clear();
    }

    private void failedAuthenticationAlert(){
        makeAlertTitleHeader("User Login Failed", "UserID or Password is incorrect. Try again!!");
        // Clearing the text fields
        userIdText.clear();
        passwordText.clear();
    }

    private void failedLoaderAlert(){
        makeAlertTitleHeader("Home Page Loading Failed!", "Try again");
        // Clearing the text fields
        userIdText.clear();
        passwordText.clear();
    }

    private void makeAlertTitleHeader(String title, String header){
        Alert addSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
        addSuccessAlert.setTitle(title);
        addSuccessAlert.setHeaderText(header);
        addSuccessAlert.showAndWait();


    }
    // This function loads the contents of book_details table into a Hashmap as soon as user authentication is done
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
