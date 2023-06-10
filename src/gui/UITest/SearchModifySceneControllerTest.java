package gui.UITest;
import dataStructures.myHashMap;
import dataStructures.myLinkedList;
import gui.controllers.SearchModifySceneController;
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
public class SearchModifySceneControllerTest {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneFxml/SearchModifyScene.fxml"));
        Parent rootNode = loader.load();
        Scene scene = new Scene(rootNode);

        loadDummyDataToTable();
        SearchModifySceneController searchBookSceneController = loader.getController();
        searchBookSceneController.setBookList(bookList);
        searchBookSceneController.setBookTable(bookTable);

        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    @Test
    void bookDoesNotExistAlert(FxRobot robot){
        assertBookDoesNotExistAlertHeader(robot);
        //click on the ok alert
        robotWaitToClickString(robot, "No");
    }

    @Test
    void bookDoesNotExistNavigateToAddScene(FxRobot robot){
        assertBookDoesNotExistAlertHeader(robot);
        //click on the ok alert
        robotWaitToClickString(robot, "Yes");
        robotNavigator.confirmUniqueElement("Add Book");
    }

    @Test
    void bookExistsNavigateToModifyScene(FxRobot robot){
        robotWriter.populateSearchTextFieldWithRealBook();
        String pageQuery = "Search Book";
        String uniqueElement = "Modify Book";
        robotNavigator.confirmPageQueryUniqueElement(pageQuery, uniqueElement);

    }

    private void assertBookDoesNotExistAlertHeader(FxRobot robot){
        String expectedHeader = "Book title entered doesn't exist in DB";
        robotWriter.searchADummyBook();
        robotAlertFinder.findAndAssertAlertExpectedHeader(expectedHeader);
    }

    private void robotWaitToClickString(FxRobot robot, String state){
        //wait until the alert shows up
        robot.sleep(500);
        robot.clickOn(state);
    }

    private void loadDummyDataToTable(){
        bookTable.put("TitleTest", "AuthorTest");
    }



}
