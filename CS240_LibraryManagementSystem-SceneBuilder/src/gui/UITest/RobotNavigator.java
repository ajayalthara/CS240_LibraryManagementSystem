package gui.UITest;
import javafx.scene.control.Button;
import org.testfx.api.FxRobot;


import static org.testfx.assertions.api.Assertions.assertThat;
public class RobotNavigator {
    FxRobot robot;

    public RobotNavigator(FxRobot robot){
        this.robot = robot;
    }

    public void confirmPageQueryUniqueElement(String pageQuery, String uniqueElement){
        clickPageQueryButton(pageQuery);

        confirmUniqueElement(uniqueElement);
    }

    public void clickPageQueryButton(String pageQuery){
        Button removeBookPage = robot.lookup(pageQuery).queryButton();
        robot.clickOn(removeBookPage);
    }

    public void clickButton(String buttonText){
        robot.clickOn(buttonText);
    }

    public void confirmUniqueElement(String uniqueElement){
        Button uniqueButton = robot.lookup(uniqueElement).queryButton();
        assertThat(uniqueButton).isNotNull();

    }
}
