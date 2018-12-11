package View;

import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class UserHomePage extends HomePage {

    private Update updateWindow;
    private HomePage homePage;
    private Controller controller;
    private Stage stage;
    private InsertVacation insertVacation;

    public void setController(Controller controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;

    }


    public void update(ActionEvent actionEvent) {
        newStage("Update.fxml", "עדכון פרטים אישיים", updateWindow, 600, 400,controller);
    }

    public void delete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("כן");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("לא");
        alert.setContentText("האם אתה בטוח שברצונך למחוק את שם המשתמש מהמערכת?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            // Close program
                controller.deleteUser(controller.getUserName());
                stage.close();
                newStage("HomePage.fxml", "כניסת משתמש רשום", homePage, 940, 581,controller);
        } else {
            // ... user chose CANCEL or closed the dialog
            //windowEvent.consume();

        }
    }

    public void search(ActionEvent actionEvent) {
    }

    public void sellTickets(ActionEvent actionEvent) {
        newStage("InsertVacation.fxml", "", insertVacation, 760,430,controller);
    }


}
