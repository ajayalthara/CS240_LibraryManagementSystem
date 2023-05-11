package gui;

import dataStructures.myHashMap;
import database.DbConnectionWrapper;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;

public class appHomeGui extends Application {
    private Button addBooksButton, removeBooksButton, modifyBooksButton;
    private Scene homeScene, addBooksScene;
    private Group root, root2;
    // Method that is called from main class to launch the GUI
    public static void begin(String[] args) {
        launch(args);
    }
    // Overriding the start method from Applications class
    public void start(Stage stage) throws Exception {
        // Creates the stage
        stage.setTitle("Library Management System");
        stage.setWidth(1000);
        stage.setHeight(700);
        stage.setResizable(true); // Determines if the window can be resized
        // Creates the root node which is of type Group
        root = new Group();
        root2 = new Group();
        // Creates the home and addBook scene
        homeScene = new Scene(root, 1000, 700, Color.LIGHTBLUE);
        addBooksScene = new Scene(root2, 1000, 700, Color.LIGHTBLUE);
        // Creating Icons
        Image windowIcon = new Image("bookIcon.png");
        stage.getIcons().add(windowIcon);
        // Adding buttons to home scene
        addBooks(stage);
        removeBooks();
        modifyBooks();
        getBookInfoScene(stage);
        //goHome();
        // Setting the Scene and showing the stage
        stage.setScene(homeScene);
        stage.show();
    }

    private void goHome() {

    }

    private void addBooks(Stage stage) { // creates a button to add books
        addBooksButton = new Button();
        addBooksButton.setText("Add Books");
        addBooksButton.setLayoutX(100); // button location
        addBooksButton.setLayoutY(350);
        addBooksButton.setPrefSize(120,40); // sets size of button
        root.getChildren().add(addBooksButton);
        addBooksButton.setOnAction(e -> stage.setScene(addBooksScene));
    }

    private void removeBooks() { // creates a button to remove books
        removeBooksButton = new Button();
        removeBooksButton.setText("Remove Books");
        removeBooksButton.setLayoutX(400);
        removeBooksButton.setLayoutY(350);
        removeBooksButton.setPrefSize(120,40);
        root.getChildren().add(removeBooksButton);
    }

    private void modifyBooks() { // creates a button to modify books
        modifyBooksButton = new Button();
        modifyBooksButton.setText("Modify Books");
        modifyBooksButton.setLayoutX(700);
        modifyBooksButton.setLayoutY(350);
        modifyBooksButton.setPrefSize(120,40);
        root.getChildren().add  (modifyBooksButton);
    }

    private void getBookInfoScene(Stage stage) {
        //Adding new hashmap where the values will be stored
        myHashMap titleAuthor = new myHashMap();


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

//            // Puts title data into a hashmap
//            titleAuthor.put(title,author);
//            //Printing from hashMap
//            System.out.println("Printing from hashmap");
//            System.out.println(titleAuthor.get(title));

        });

        Button goHome = new Button();
        goHome.setText("HomeScreen");
        goHome.setLayoutX(100);
        goHome.setLayoutY(500);
        goHome.setOnAction(e -> stage.setScene(homeScene));

        root2.getChildren().addAll(titleBox, authorBox, genreBox, submit, goHome);
    }

}