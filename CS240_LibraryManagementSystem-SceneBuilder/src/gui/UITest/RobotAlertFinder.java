package gui.UITest;
import javafx.stage.Modality;
import org.testfx.api.FxRobot;
import javafx.stage.Window;
import static org.testfx.assertions.api.Assertions.assertThat;
import javafx.scene.control.DialogPane;

public class RobotAlertFinder {
    FxRobot robot;
    public RobotAlertFinder(FxRobot robot){
        this.robot = robot;
    }

    public void findAndAssertAlertExpectedHeader(String expectedHeader){
        Window foundWindow = getAlertWindow();
        assertThat(foundWindow).isNotNull();
        DialogPane dialogPane = (DialogPane) foundWindow.getScene().getRoot();
        assertThat(dialogPane.getHeaderText()).isEqualTo(expectedHeader);

    }

    private Window getAlertWindow(){
        return robot.robotContext().getWindowFinder().listWindows().stream()
                .filter(window -> window instanceof javafx.stage.Stage)
                .filter(window -> ((javafx.stage.Stage) window).getModality() == Modality.APPLICATION_MODAL)
                .findFirst()
                .orElse(null);
    }
}
