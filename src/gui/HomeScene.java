package gui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import gui.AddBookScene;

public class HomeScene {
    private static Scene homeScene;
    public HomeScene(Stage stage) {
        // Initializing buttons and group
        Button addBooksButton, removeBooksButton, modifyBooksButton;
        Group homeSceneGroup = new Group();
        // Adding the group to the scene
        homeScene = new Scene(homeSceneGroup, 1000, 700, Color.LIGHTBLUE);
        //Adding buttons on the home group
        addBooksButton = new Button();
        addBooksButton.setText("Add Books");
        addBooksButton.setLayoutX(100); // button location
        addBooksButton.setLayoutY(350);
        addBooksButton.setPrefSize(120,40); // sets size of button
        addBooksButton.setOnAction(e -> stage.setScene(AddBookScene.getScene()));

        removeBooksButton = new Button();
        removeBooksButton.setText("Remove Books");
        removeBooksButton.setLayoutX(400);
        removeBooksButton.setLayoutY(350);
        removeBooksButton.setPrefSize(120,40);

        modifyBooksButton = new Button();
        modifyBooksButton.setText("Modify Books");
        modifyBooksButton.setLayoutX(700);
        modifyBooksButton.setLayoutY(350);
        modifyBooksButton.setPrefSize(120,40);
        // Adding all three buttons to the group
        homeSceneGroup.getChildren().addAll(addBooksButton,removeBooksButton,modifyBooksButton);
    }
   public static Scene getScene(){
       return homeScene;
        
   }
}