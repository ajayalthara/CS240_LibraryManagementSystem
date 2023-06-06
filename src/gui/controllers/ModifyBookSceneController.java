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

public class ModifyBookSceneController {
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
    private TextField modifyBookTitleText;
    @FXML
    private TextField modifyBookAuthorText;
    @FXML
    private TextField modifyBookGenreText;
    private String oldTitle, newTitle, newAuthor, newGenre;
    public void setModifyBookTitleText(String modifyBookTitleString) {
        this.modifyBookTitleText.setText(modifyBookTitleString);
        oldTitle=modifyBookTitleString;
    }
    public void setModifyBookAuthorText(String modifyBookAuthorString) {
        this.modifyBookAuthorText.setText(modifyBookAuthorString);
    }
    public void setModifyBookGenreText(String modifyBookGenreString) {
        this.modifyBookGenreText.setText(modifyBookGenreString);
    }
    // Modifying book record
    public void modifyBookSubmit(ActionEvent event) throws IOException {
        // Taking all the text field values in the screen into strings
        // The substring function is called to limit the number of characters to what is allowed in DB tables
        newTitle = modifyBookTitleText.getText();
        if(newTitle.length() > 50){
            newTitle = newTitle.substring(0,49);
        }
        newAuthor = modifyBookAuthorText.getText();
        if(newAuthor.length() > 30){
            newAuthor = newAuthor.substring(0,29);
        }
        newGenre = modifyBookGenreText.getText();
        if(newGenre.length() > 30){
            newGenre = newGenre.substring(0,29);
        }
        DbConnectionWrapper dbWrapper = new DbConnectionWrapper();
        try {
            dbWrapper.connect();
            // Deleting the book from DB and program memory
            int rowsAffected = dbWrapper.executeUpdate("update book_details set title = ?, genre=?,author=? WHERE title = ?", newTitle,newGenre,newAuthor,oldTitle);
            bookTable.remove(oldTitle);
            bookTable.put(newTitle,newAuthor);
            dbWrapper.disconnect();
            // Alert showing successful deletion of record
            Alert modifySuccessAlert = new Alert(Alert.AlertType.INFORMATION);
            modifySuccessAlert.setTitle("Success!");
            modifySuccessAlert.setHeaderText("Successfully modified the record");
            modifySuccessAlert.showAndWait();

        } catch (SQLException ex) {
            // DB connection error alert
            Alert deleteFailureAlert = new Alert(Alert.AlertType.INFORMATION);
            deleteFailureAlert.setTitle("Failed");
            deleteFailureAlert.setHeaderText("DB Error. Check connection parameters");
            deleteFailureAlert.showAndWait();
            throw new RuntimeException(ex);
        }
        // Clearing the text fields after each book addition
        modifyBookTitleText.clear();
        modifyBookAuthorText.clear();
        modifyBookGenreText.clear();
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
    //Function to go back to search scene
    public void loadSearchScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneFxml/SearchModifyScene.fxml"));
        rootNode = loader.load();
        //Search book controller object creation to pass the list and table
        SearchModifySceneController searchModifySceneController = loader.getController();
        searchModifySceneController.setBookTable(bookTable);
        searchModifySceneController.setBookList(bookList);
        // Setting the stage for search book scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.show();
    }
}
