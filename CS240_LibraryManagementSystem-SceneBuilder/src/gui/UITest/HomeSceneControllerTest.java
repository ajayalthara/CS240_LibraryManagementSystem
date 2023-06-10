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
public class HomeSceneControllerTest {
    private RobotNavigator robotNavigator;

    @BeforeEach
    void setUp(FxRobot robot) {
        robotNavigator = new RobotNavigator(robot);
    }

    @Start
    private void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sceneFxml/HomePageScene.fxml"));
        Parent rootNode = loader.load();
        Scene scene = new Scene(rootNode);

        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    //check to see if we have gone to the right addBook window by looking for the add button
    @Test
    void goToAddBookPage(FxRobot robot){
        String pageQuery = "Add New Books";
        String uniqueElement = "Add Book";
        robotNavigator.confirmPageQueryUniqueElement(pageQuery, uniqueElement);
    }

    //check to see if we have gone to the right removeBook window by looking for the add button
    @Test
    void goToRemoveBookPage(FxRobot robot){
        String pageQuery = "Remove Books";
        String uniqueElement = "Delete";
        robotNavigator.confirmPageQueryUniqueElement(pageQuery, uniqueElement);
    }

    //check to see if we have gone to the right searchAndModify window by looking for the add button
    @Test
    void goToSearchAndModifyPage(FxRobot robot){
        String pageQuery = "Search & Modify";
        String uniqueElement = "Search Book";
        robotNavigator.confirmPageQueryUniqueElement(pageQuery, uniqueElement);
    }



}
