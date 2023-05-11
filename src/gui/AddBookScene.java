package gui;

import database.DbConnectionWrapper;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddBookScene {
    private static Scene addBookScene;
    public AddBookScene(Stage stage) {
        // Initializing the group and scene
        Group addBookSceneGroup = new Group();
        addBookScene = new Scene(addBookSceneGroup, 1000, 700, Color.LIGHTBLUE);
        // Adding all the text fields, labels and buttons
        TextField getTitle = new TextField();
        TextField getAuthor = new TextField();
        TextField getGenre = new TextField();

        Label titleLabel = new Label("Title:");
        Label authorLabel = new Label("Author:");
        Label genreLabel = new Label("Genre:");

        HBox titleBox = new HBox();
        HBox authorBox = new HBox();
        HBox genreBox = new HBox();

        titleBox.getChildren().addAll(titleLabel, getTitle);
        authorBox.getChildren().addAll(authorLabel, getAuthor);
        genreBox.getChildren().addAll(genreLabel, getGenre);

        titleBox.setSpacing(24);
        authorBox.setSpacing(10);
        genreBox.setSpacing(15);
        titleBox.setLayoutX(100);
        titleBox.setLayoutY(250);
        authorBox.setLayoutX(100);
        authorBox.setLayoutY(300);
        genreBox.setLayoutX(100);
        genreBox.setLayoutY(350);
        // Adding the submit button. Clicking this will put the data in the DB
        Button submit = new Button();
        submit.setText("Submit");
        submit.setLayoutX(100);
        submit.setLayoutY(400);
        submit.setOnAction(e -> {
            // gets info from text boxes when submit clicked
            String title = getTitle.getText();
            String author = getAuthor.getText();
            String genre = getGenre.getText();
            System.out.println(title);
            System.out.println(author);
            System.out.println(genre);
            // Posting details into the DB
            DbConnectionWrapper dbWrapper = new DbConnectionWrapper();
            try {
                dbWrapper.connect();
                int rowsAffected = dbWrapper.executeUpdate("INSERT INTO book_details (title, author, genre) values (?,?,?)",title,author,genre);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button goHome = new Button();
        goHome.setText("HomeScreen");
        goHome.setLayoutX(100);
        goHome.setLayoutY(500);
        goHome.setOnAction(e -> stage.setScene(HomeScene.getScene()));
        //Adding all objects in the Add book page
        addBookSceneGroup.getChildren().addAll(titleBox, authorBox, genreBox, submit, goHome);
    }
   public static Scene getScene(){
       return addBookScene;
   }
}