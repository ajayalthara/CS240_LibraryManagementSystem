package gui.UITest;
import dataStructures.myHashMap;
import database.DbConnectionWrapper;
import gui.controllers.AddBookSceneController;
import javafx.scene.control.Alert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.junit.jupiter.api.Assertions.*;

import static org.testfx.assertions.api.Assertions.assertThat;


import dataStructures.myLinkedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


@ExtendWith(ApplicationExtension.class)
public class AddBookSceneControllerTest {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneFxml/AddBookScene.fxml"));
        Parent rootNode = loader.load();
        Scene scene = new Scene(rootNode);

        loadDummyDataToTable();
        AddBookSceneController addBookSceneController = loader.getController();
        addBookSceneController.setBookList(bookList);
        addBookSceneController.setBookTable(bookTable);

        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    @Test
    void saveInCentralDBAlert(FxRobot robot){
        String expectedHeader = "Data exists locally that is not saved in central database";
        addBookWith(robot);
        robotNavigator.clickPageQueryButton("Home Screen");
        robotAlertFinder.findAndAssertAlertExpectedHeader(expectedHeader);
        robotWaitToClickAlertOk(robot);

    }

    @Test
    void emptyTextFieldsAlert(FxRobot robot){
        String expectedHeader = "one or more text fields are empty, please try again.";
        robot.clickOn("#addBookSubmit");
        robotAlertFinder.findAndAssertAlertExpectedHeader(expectedHeader);
        robotWaitToClickAlertOk(robot);
    }


    @Test
    void textFieldEmptyAfterAddingBook(FxRobot robot) {
        addBookWith(robot);

        // Check that the text fields have been cleared.
        assertsThatTextFieldsAreEmpty(robot);

    }

    @Test
    void lastNodeInLLUpdated(FxRobot robot){
        addBookWith(robot);

        //get the latest node
        int linkedListSize = bookList.sizeOfList();
        String latestNodeData = bookList.getNodeDataByIndex(linkedListSize-1);

        //check that the linked list was updated.
        assertEquals(new RobotWriter(robot).getExpectedNodeText(), latestNodeData);
        //System.out.println(latestNodeData);
        //System.out.println(linkedListSize);

    }

    @Test
    void goToHomePage(FxRobot robot){
        String pageQuery = "Home Screen";
        String uniqueElement = "Add New Books";
        robotNavigator.confirmPageQueryUniqueElement(pageQuery, uniqueElement);
    }

    private void addBookWith(FxRobot robot){
        robotWriter.addABook();
        robotWaitToClickAlertOk(robot);

    }

    private void robotWaitToClickAlertOk(FxRobot robot){
        //wait until the alert shows up
        robot.sleep(500);
        robot.clickOn("OK");
    }

    private void assertsThatTextFieldsAreEmpty(FxRobot robot){
        assertThat(robot.lookup("#titleText").queryTextInputControl().getText()).isEmpty();
        assertThat(robot.lookup("#authorText").queryTextInputControl().getText()).isEmpty();
        assertThat(robot.lookup("#genreText").queryTextInputControl().getText()).isEmpty();
    }

    private void loadDummyDataToTable(){
        bookTable.put("TitleTest", "AuthorTest");
    }






}
