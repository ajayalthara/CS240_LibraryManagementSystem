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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class DeleteBookSceneController {
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
    private TextField deleteBookTitleText;

    // Deleting book record
    public void deleteBookSubmit(ActionEvent event) throws IOException {
        // Taking all the text field values in the screen into strings
        // The substring function is called to limit the number of characters to what is allowed in DB tables
        String title = deleteBookTitleText.getText();
        if(title.length() > 50){
            title = title.substring(0,49);
        }
        if (bookTable.containsKey(title)) {
            // Making DB connection
            DbConnectionWrapper dbWrapper = new DbConnectionWrapper();
            try {
                dbWrapper.connect();
                // Deleting the book from DB and program memory
                int rowsAffected = dbWrapper.executeUpdate("DELETE FROM book_details WHERE title = ?", title);
                bookTable.remove(title);
                dbWrapper.disconnect();
                // Alert showing successful deletion of record
                Alert deleteSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                deleteSuccessAlert.setTitle("Success!");
                deleteSuccessAlert.setHeaderText("Successfully deleted the record");
                deleteSuccessAlert.showAndWait();

            } catch (SQLException ex) {
                // DB connection error alert
                Alert deleteFailureAlert = new Alert(Alert.AlertType.INFORMATION);
                deleteFailureAlert.setTitle("Failed");
                deleteFailureAlert.setHeaderText("DB Error. Check connection parameters");
                deleteFailureAlert.showAndWait();
                throw new RuntimeException(ex);
            }
        } else {
            // Alert showing book title entered doesn't exist
            Alert bookNotExistAlert = new Alert(Alert.AlertType.INFORMATION);
            bookNotExistAlert.setTitle("Non existent book!!");
            bookNotExistAlert.setHeaderText("Book title entered doesn't exist in DB");
            bookNotExistAlert.showAndWait();
        }
        // Clearing the text fields after each book addition
        deleteBookTitleText.clear();
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
