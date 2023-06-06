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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchModifySceneController {
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
    private String author, genre, title;
    @FXML
    private TextField searchBookTitleText;

    // Searching for book record
    public void searchBookSubmit(ActionEvent event) throws IOException {
        // Taking all the text field values in the screen into strings
        // The substring function is called to limit the number of characters to what is allowed in DB tables
        title = searchBookTitleText.getText();
        if(title.length() > 50){
            title = title.substring(0,49);
        }

        if (bookTable.containsKey(title)) {
            // Making DB connection
            DbConnectionWrapper dbWrapper = new DbConnectionWrapper();
            try {
                dbWrapper.connect();
                // Searching the book from DB and fetching the details
                ResultSet bookDetails = dbWrapper.executeQueryWithParam("Select author, genre FROM book_details WHERE title = ?", title);
                while (bookDetails.next()) {
                    author = bookDetails.getString(1);
                    genre = bookDetails.getString(2);
                }
                bookDetails.close();
                dbWrapper.disconnect();
            } catch (SQLException ex) {
                // DB connection error alert
                Alert deleteFailureAlert = new Alert(Alert.AlertType.INFORMATION);
                deleteFailureAlert.setTitle("Failed");
                deleteFailureAlert.setHeaderText("DB Error. Check connection parameters");
                deleteFailureAlert.showAndWait();
                throw new RuntimeException(ex);
            }
            // Navigate to modify book scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneFxml/ModifyBookScene.fxml"));
            rootNode = loader.load();
            //Scene controller object creation for passing data
            ModifyBookSceneController modifyBookSceneController=loader.getController();
            modifyBookSceneController.setBookTable(bookTable);
            modifyBookSceneController.setBookList(bookList);
            modifyBookSceneController.setModifyBookTitleText(title);
            modifyBookSceneController.setModifyBookAuthorText(author);
            modifyBookSceneController.setModifyBookGenreText(genre);
            // Setting the stage for modify book scene
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(rootNode);
            stage.setScene(scene);
            stage.show();
        } else {
            // Setting the button type to include Yes and No buttons instead of default OK and Cancel
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");
            // Alert showing book title entered doesn't exist
            Alert bookNotExistAlert = new Alert(Alert.AlertType.CONFIRMATION);
            bookNotExistAlert.setTitle("Non existent book!!");
            bookNotExistAlert.setHeaderText("Book title entered doesn't exist in DB");
            bookNotExistAlert.setContentText("Do you want to add a new book record??");
            bookNotExistAlert.getButtonTypes().setAll(buttonTypeYes,buttonTypeNo);
            bookNotExistAlert.showAndWait();
            if(bookNotExistAlert.showAndWait().get() == buttonTypeYes){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneFxml/AddBookScene.fxml"));
                rootNode = loader.load();
                //Add book controller object creation to pass the list and table
                AddBookSceneController addBookSceneController = loader.getController();
                addBookSceneController.setBookList(bookList);
                addBookSceneController.setBookTable(bookTable);
                addBookSceneController.setTitleText(title);
                // Setting the stage for add book scene
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(rootNode);
                stage.setScene(scene);
                stage.show();
            }
        }
        // Clearing the text fields after each book addition
        searchBookTitleText.clear();
    }
    // Function that navigates to home screen if there is no data in linked list
    public void loadHomePage(ActionEvent event) throws IOException {
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
    }
}
