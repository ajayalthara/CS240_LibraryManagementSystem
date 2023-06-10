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
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddBookSceneController {
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
    private TextField titleText;
    @FXML
    private TextField authorText;
    @FXML
    private TextField genreText;
    @FXML
    private Button addBookSubmit;
    public void setTitleText(String titleString) {
        this.titleText.setText(titleString);
    }
    // Adding a new book record into the linked list/local storage
    public void addBookSubmit(ActionEvent event) throws IOException {
        // Taking all the text field values in the screen into strings
        // The substring function is called to limit the number of characters to what is allowed in DB tables
        String title = titleText.getText();
        if(title.length() > 50){ // just a cap for title length
            title = title.substring(0,49);
        }
        String author = authorText.getText();
        if(author.length() > 30){//just a cap for author length
            author = author.substring(0,29);
        }
        String genre = genreText.getText();
        if(genre.length() > 30){ //just a cap for genre length
            genre = genre.substring(0,29);
        }

        if(title.length() > 0 && author.length() > 0 && genre.length() > 0){
            // Concatenating the book details for storage into linked list
            String listEntry = title+"|"+author+"|"+genre;
            bookList.addNode(listEntry); //add a node title|author|genre
            // Alert showing successful addition of record
            makeAlertTitleHeader("Success!", "Successfully saved the record locally");
            // Clearing the text fields after each book addition
            titleText.clear();
            genreText.clear();
            authorText.clear();

        }else{
            makeAlertTitleHeader("Failed!", "one or more text fields are empty, please try again.");
        }

    }

    private void makeAlertTitleHeader(String title, String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();

    }

    // Function that navigates to home screen if there is no data in linked list
    public void loadHomePage(ActionEvent event) throws IOException {
        if(bookList.isListEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneFxml/HomePageScene.fxml"));
            rootNode = loader.load();
            // Home page controller object creation to pass the list and table
            HomeSceneController homeSceneController = loader.getController();
            homeSceneController.setBookTable(bookTable);
            homeSceneController.setBookList(bookList);
            // Setting stage for home scene
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(rootNode);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert listEmptyAlert = new Alert(Alert.AlertType.WARNING);
            listEmptyAlert.setTitle("Data Loss Alert");
            listEmptyAlert.setHeaderText("Data exists locally that is not saved in central database");
            listEmptyAlert.setContentText("Post all new data into central DB by clicking 'Save in Central DB'");
            listEmptyAlert.showAndWait();
        }
    }
    // Function to move all local records in linked list into Central DB
    public void saveToCentralDb(ActionEvent event) throws IOException{
        if(bookList.isListEmpty()){
            Alert listEmptyAlert = new Alert(Alert.AlertType.WARNING);
            listEmptyAlert.setTitle("Empty List!");
            listEmptyAlert.setHeaderText("No data available to post in central database");
            listEmptyAlert.setContentText("Save book details locally before transferring them to central DB");
            listEmptyAlert.showAndWait();
        } else {
            // Making DB connection
            DbConnectionWrapper dbWrapper = new DbConnectionWrapper();
            try {
                dbWrapper.connect();
                ArrayList<String> dbEntriesBeforeSplit = bookList.getAllNodeDataInArray();
                for (String element : dbEntriesBeforeSplit){
                    String title =  element.split("\\|")[0];
                    String author = element.split("\\|")[1];
                    String genre = element.split("\\|")[2];
                    // Posting each book record in DB and the program memory (HashTable)
                    int rowsAffected = dbWrapper.executeUpdate("INSERT INTO book_details (title, author, genre) values (?,?,?)",title,author,genre);
                    bookTable.put(title,author);
                }
                dbWrapper.disconnect();
                // Alert showing successful addition of record
                Alert addSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                addSuccessAlert.setTitle("Success!");
                addSuccessAlert.setHeaderText("Successfully added the record");
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
        }
    }
}
