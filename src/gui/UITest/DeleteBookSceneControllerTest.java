package gui.UITest;
import dataStructures.myHashMap;
import dataStructures.myLinkedList;
import gui.controllers.AddBookSceneController;
import gui.controllers.DeleteBookSceneController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class DeleteBookSceneControllerTest {
    private myLinkedList<String> bookList = new myLinkedList();
    private myHashMap<String, String> bookTable = new myHashMap<>();
    private RobotWriter robotWriter;
    private RobotNavigator robotNavigator;
    private RobotAlertFinder robotAlertFinder;

    @BeforeEach
    void setUp(FxRobot robot) {
        robotWriter = new RobotWriter(robot);
        robotNavigator = new RobotNavigator(robot);
        robotAlertFinder = new RobotAlertFinder(robot);
    }

    @Start
    private void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneFxml/DeleteBookScene.fxml"));
        Parent rootNode = loader.load();
        Scene scene = new Scene(rootNode);

        loadDummyDataToTable();
        DeleteBookSceneController deleteBookSceneController = loader.getController();
        deleteBookSceneController.setBookList(bookList);
        deleteBookSceneController.setBookTable(bookTable);

        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    @Test
    void goToHomePage(FxRobot robot){
        String pageQuery = "Home Screen";
        String uniqueElement = "Add New Books";
        robotNavigator.confirmPageQueryUniqueElement(pageQuery, uniqueElement);
    }

    @Test
    void nonExistentBookAlert(FxRobot robot){
        String expectedHeader = "Book title entered doesn't exist in DB";
        robotWriter.deleteADummyBook();
        robotAlertFinder.findAndAssertAlertExpectedHeader(expectedHeader);
        //click on the ok alert
        robotWaitToClickOk(robot);
    }
    @Test
    void successfulDeletionAlert(FxRobot robot){
        String expectedHeader = "Successfully deleted the record";
        robotWriter.deleteARealBook();
        robotAlertFinder.findAndAssertAlertExpectedHeader(expectedHeader);
        //click on the ok alert
        robotWaitToClickOk(robot);
    }
    private void robotWaitToClickOk(FxRobot robot){
        //wait until the alert shows up
        robot.sleep(500);
        robot.clickOn("OK");
    }

    private void loadDummyDataToTable(){
        bookTable.put("TitleTest", "AuthorTest");
    }






}
