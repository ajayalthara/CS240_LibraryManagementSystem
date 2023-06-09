package gui.controllers;


import dataStructures.myHashMap;
import dataStructures.myLinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeSceneController {
    private Stage stage;
    private Scene scene;
    private Parent rootNode;
    // Creates the hash table and the linked list
    private myHashMap<String, String> bookTable = new myHashMap<>();
    private myLinkedList bookList = new myLinkedList();
    public void setBookList(myLinkedList bookList) {
        this.bookList = bookList;
    }
    public void setBookTable(myHashMap bookTable) {
        this.bookTable = bookTable;
    }

    public void addBook(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneFxml/AddBookScene.fxml"));
        rootNode = loader.load();
        //Add book controller object creation to pass the list and table
        AddBookSceneController addBookSceneController = loader.getController();
        addBookSceneController.setBookList(bookList);
        addBookSceneController.setBookTable(bookTable);
        // Setting the stage for add book scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.show();
    }
    public void deleteBook(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneFxml/DeleteBookScene.fxml"));
        rootNode = loader.load();
        //Delete book controller object creation to pass the list and table
        DeleteBookSceneController deleteBookSceneController = loader.getController();
        deleteBookSceneController.setBookTable(bookTable);
        deleteBookSceneController.setBookList(bookList);
        // Setting the stage for delete book scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.show();
    }
    public void searchAndModifyBook(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneFxml/SearchModifyScene.fxml"));
        rootNode = loader.load();
        //Delete book controller object creation to pass the list and table
        SearchModifySceneController searchModifySceneController = loader.getController();
        searchModifySceneController.setBookTable(bookTable);
        searchModifySceneController.setBookList(bookList);
        // Setting the stage for delete book scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.show();
    }
}
