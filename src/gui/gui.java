package gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class gui extends Application {
    private Button addBooksButton, removeBooksButton, modifyBooksButton;
    private Scene initialScene, addBooksScene;
    private Group root, root2;

    public static void begin(String[] args) {

        launch(args);
    }

    public void start(Stage stage) throws Exception {
        // creates the stage
        stage.setTitle("Library Management System");
        stage.setWidth(1000);
        stage.setHeight(700);
        stage.setResizable(true); // determines if the window can be resized
        // creates the scenes
        root = new Group();
        initialScene = new Scene(root, 1000, 700, Color.LIGHTBLUE);
        root2 = new Group();
        addBooksScene = new Scene(root2, 1000, 700, Color.LIGHTBLUE);
        // adds buttons to initialScene
        addBooks(stage);
        removeBooks();
        modifyBooks();
        getBookInfoScene();
        stage.setScene(initialScene);
        stage.show();


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
        root.getChildren().add(modifyBooksButton);
    }

    private void getBookInfoScene() {
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
        });
        root2.getChildren().addAll(titleBox, authorBox, genreBox, submit);
    }

}