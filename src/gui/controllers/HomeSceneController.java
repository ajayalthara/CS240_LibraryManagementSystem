package gui.controllers;


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
    public void addBook(ActionEvent event) throws IOException {
        rootNode = FXMLLoader.load(getClass().getResource("../AddBookScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.show();
    }
    public void deleteBook(ActionEvent event){
        System.out.println("Clicking on delete book");
    }
    public void modifyBook(ActionEvent event){
        System.out.println("Clicking on modify book");
    }
    public void searchBook(ActionEvent event){
        System.out.println("Clicking on modify book");
    }
    public void loadHomePage(ActionEvent event) throws IOException {
        rootNode = FXMLLoader.load(getClass().getResource("../HomePageScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.show();
    }
}
