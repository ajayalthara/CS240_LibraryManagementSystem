package gui.UITest;
import org.testfx.api.FxRobot;

public class RobotWriter {
    FxRobot robot;

    public RobotWriter(FxRobot robot){
        this.robot = robot;
    }

    public void deleteADummyBook(){
        populateDeleteTextField();
        robot.clickOn("#deleteBookButton");
    }

    public void deleteARealBook(){
        populateDeleteTextFieldReal();
        robot.clickOn("#deleteBookButton");
    }

    public void searchADummyBook(){
        populateSearchTextField();
        robot.clickOn("#searchBookButton");
    }

    public void addABook(){
        populateAddBookField();
        robot.clickOn("#addBookSubmit");
    }

    public void populateDeleteTextField(){
        String nonExistentBook = "------------------";
        String titleTextField = "#deleteBookTitleText";
        robot.clickOn(titleTextField).write(nonExistentBook);
    }

    public void populateDeleteTextFieldReal(){
        String existingBook = "TitleTest";
        String titleTextField = "#deleteBookTitleText";
        robot.clickOn(titleTextField).write(existingBook);
    }

    public void populateSearchTextField(){
        String nonExistentBook = "------------------";
        String titleTextField = "#searchBookTitleText";
        robot.clickOn(titleTextField).write(nonExistentBook);
    }

    public void populateSearchTextFieldWithRealBook(){
        String existingDummyTitle = "TitleTest";
        String titleTextField = "#searchBookTitleText";
        robot.clickOn(titleTextField).write(existingDummyTitle);
    }

    public void populateAddBookField(){
        String titleText = "TitleTest";
        String authorText = "AuthorTest";
        String genreText = "GenreTest";
        robot.clickOn("#titleText").write(titleText);
        robot.clickOn("#authorText").write(authorText);
        robot.clickOn("#genreText").write(genreText);
    }

    public void populateUserAuthFieldDummy(){
        String user = "2-3-45-1-2-56-";
        String password ="-1--4-2-56--2";
        robot.clickOn("#userIdText").write(user);
        robot.clickOn("#passwordText").write(password);
    }

    public void populateUserAuthFieldReal(){
        String user = "admin";
        String password ="password";
        robot.clickOn("#userIdText").write(user);
        robot.clickOn("#passwordText").write(password);
    }

    public void populateModifyBookFields(){
        String newAuthor = "newAuthor";
        String newGenre = "newGenre";
        String newTitle = "newTitleTest";

        robot.clickOn("#modifyBookTitleText").write(newTitle);
        robot.clickOn("#modifyBookGenreText").write(newGenre);
        robot.clickOn("#modifyBookAuthorText").write(newAuthor);

    }


    public String getExpectedNodeText(){
        return "TitleTest"+"|"+"AuthorTest"+"|"+"GenreTest";
    }


}
