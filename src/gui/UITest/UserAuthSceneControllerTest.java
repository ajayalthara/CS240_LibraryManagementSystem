package gui.UITest;
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
public class UserAuthSceneControllerTest {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneFxml/UserAuthScene.fxml"));
        Parent rootNode = loader.load();
        Scene scene = new Scene(rootNode);

        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    @Test
    void checkWrongInputAlert(FxRobot robot){
        String expectedHeader = "UserID or Password is incorrect. Try again!!";
        robotWriter.populateUserAuthFieldDummy();
        robotNavigator.clickPageQueryButton("#userLoginButton");
        robotAlertFinder.findAndAssertAlertExpectedHeader(expectedHeader);
        robotWaitToClickOk(robot);
    }

    @Test
    void checkCorrectUserInput(){
        robotWriter.populateUserAuthFieldReal();
        robotNavigator.clickPageQueryButton("#userLoginButton");
        robotNavigator.confirmUniqueElement("Add New Books");
    }

    @Test
    void checkInputFieldEmpty(FxRobot robot){
        String expectedHeader = "one or more text fields are empty, please try again.";
        robotNavigator.clickPageQueryButton("#userLoginButton");
        robotAlertFinder.findAndAssertAlertExpectedHeader(expectedHeader);
        robotWaitToClickOk(robot);

    }

    private void robotWaitToClickOk(FxRobot robot){
        //wait until the alert shows up
        robot.sleep(500);
        robot.clickOn("OK");
    }




}
