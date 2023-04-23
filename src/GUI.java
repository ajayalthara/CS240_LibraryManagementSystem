import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application implements EventHandler<ActionEvent> {
    private Button addBooksButton;
    private Button removeBooksButton;
    private Button modifyBooksButton;
    Text text;
    public static void begin(String[] args) {

        launch(args);
    }

    public void start(Stage stage) throws Exception {

        stage.setTitle("Library Management System");

        addBooks();
        removeBooks();
        modifyBooks();
        text = new Text();
        text.setText("Removed Book: ...");
        text.setVisible(false);

        Text title = new Text();
        title.setText("Library Management System");
        title.setTranslateX(0);
        title.setTranslateY(-200);
        title.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 30));
        title.setFill(Color.WHITE);

        StackPane layout = new StackPane();
        layout.getChildren().add(addBooksButton);
        layout.getChildren().add(removeBooksButton);
        layout.getChildren().add(modifyBooksButton);
        layout.getChildren().add(text);
        layout.getChildren().add(title);


        Scene scene = new Scene(layout);
        layout.setStyle("-fx-background-color: lightblue;"); // background color

        stage.setWidth(1000);
        stage.setHeight(700);
        stage.setResizable(true); // determines if the window can be resized

        stage.setScene(scene);
        stage.show();
    }

    public void addBooks() { // creates a button to add books
        addBooksButton = new Button();
        addBooksButton.setText("Add Books");
        addBooksButton.setOnAction(this); // this makes the Action look within this class for the handle
        addBooksButton.setTranslateX(-250); // button location
        addBooksButton.setTranslateY(50);
        addBooksButton.setPrefSize(120,40); // sets size of button
    }

    public void removeBooks() { // creates a button to remove books
        removeBooksButton = new Button();
        removeBooksButton.setText("Remove Books");
        removeBooksButton.setOnAction(this);
        removeBooksButton.setTranslateX(0);
        removeBooksButton.setTranslateY(50);
        removeBooksButton.setPrefSize(120,40);
    }

    public void modifyBooks() { // creates a button to modify books
        modifyBooksButton = new Button();
        modifyBooksButton.setText("Modify Books");
        modifyBooksButton.setOnAction(this);
        modifyBooksButton.setTranslateX(250);
        modifyBooksButton.setTranslateY(50);
        modifyBooksButton.setPrefSize(120,40);
    }

    @Override
    public void handle(ActionEvent event) { // handles what happens if a button is clicked
        if(event.getSource() == addBooksButton) {
            System.out.println("WOW IT HAS BEEN CLICKED"); // Need to replace this with what happens if addBooksButton is clicked
        }
        if(event.getSource() == removeBooksButton) {
            text.setVisible(true);
        }
    }
}