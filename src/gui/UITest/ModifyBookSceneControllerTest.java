package gui.UITest;
import dataStructures.myHashMap;
import database.DbConnectionWrapper;
import gui.controllers.AddBookSceneController;
import gui.controllers.ModifyBookSceneController;
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

@ExtendWith(ApplicationExtension.class)
public class ModifyBookSceneControllerTest {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneFxml/ModifyBookScene.fxml"));
        Parent rootNode = loader.load();
        Scene scene = new Scene(rootNode);

        loadDummyDataToTable();
        ModifyBookSceneController modifyBookSceneController = loader.getController();
        modifyBookSceneController.setBookList(bookList);
        modifyBookSceneController.setBookTable(bookTable);
        modifyBookSceneController.setModifyBookTitleText("TitleTest");

        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    @Test
    void checkFieldsAreEmptyAlert(FxRobot robot){
        String expectedHeader = "one or more text fields are empty, please try again.";
        robotNavigator.clickButton("#modifyBookSubmitButton");
        robotAlertFinder.findAndAssertAlertExpectedHeader(expectedHeader);
        robot.sleep(500);
        robotNavigator.clickButton("OK");
    }

    @Test
    void goToHomePage(){
        String pageQuery = "Home Screen";
        String uniqueElement = "Add New Books";
        robotNavigator.confirmPageQueryUniqueElement(pageQuery, uniqueElement);

    }

    @Test
    void goToSearchPage(){
        String pageQuery = "Search Books";
        String uniqueElement = "Search Book";
        robotNavigator.confirmPageQueryUniqueElement(pageQuery, uniqueElement);

    }

    @Test
    void checkSuccessfulModification(FxRobot robot){
        String expectedHeader = "Successfully modified the record";
        robotWriter.populateModifyBookFields();
        robotNavigator.clickButton("#modifyBookSubmitButton");
        robotAlertFinder.findAndAssertAlertExpectedHeader(expectedHeader);
        robot.sleep(500);
        robotNavigator.clickButton("OK");

    }




    private void loadDummyDataToTable(){
        bookTable.put("TitleTest", "AuthorTest");
    }
}
