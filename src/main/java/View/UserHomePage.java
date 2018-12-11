package View;

import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class UserHomePage extends HomePage {

    private Update updateWindow;
    private HomePage homePage;
    private Controller controller;
    private Stage stage;

    public javafx.scene.control.Label lbl_user;
    public javafx.scene.control.TextField tf_origin;
    public javafx.scene.control.TextField tf_destination;
    public javafx.scene.control.DatePicker dp_arrival;
    public javafx.scene.control.DatePicker dp_departure;
    public javafx.scene.control.TextField tf_numOfTickets;


    public void setController(Controller controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
        lbl_user.setText("שלום " + controller.getUserName());
        super.setImage();
        tooltip.setText("\nהכנס מיקום בפורמט:\n"+"עיר,מדינה"+"\n");
        tf_origin.setTooltip(tooltip);
        tf_destination.setTooltip(tooltip);


    }


    public void update(ActionEvent actionEvent) {
        newStage("Update.fxml", "עדכון פרטים אישיים", updateWindow, 721, 619,controller);
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
        super.search();
    }

    public void sellTickets(ActionEvent actionEvent) {
    }

    public void logOut(ActionEvent actionEvent){
        stage.close();
        controller.setUserName(null);
        newStage("HomePage.fxml", "כניסת משתמש רשום", homePage, 940, 581,controller);
    }



}
