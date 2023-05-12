package gui.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddBookSceneController {
    private Stage stage;
    private Scene scene;
    private Parent rootNode;
    public void addBookSubmit(ActionEvent event) throws IOException {
        rootNode = FXMLLoader.load(getClass().getResource("../AddBookScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.show();
    }

    public void loadHomePage(ActionEvent event) throws IOException {
        rootNode = FXMLLoader.load(getClass().getResource("../HomePageScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.show();
    }
}
