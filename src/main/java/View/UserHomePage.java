package View;

import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class UserHomePage extends HomePage{

    private Update updateWindow;
    private Controller controller;
    private Stage stage;

    public void setController(Controller controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;

    }


    public void update(ActionEvent actionEvent) {
        newStage("Update.fxml", "עדכון פרטים אישיים", updateWindow, 600, 400);
    }

    public void delete(ActionEvent actionEvent) {


    }

    public void search(ActionEvent actionEvent) {
    }

    public void sellTickets(ActionEvent actionEvent) {
    }
}
