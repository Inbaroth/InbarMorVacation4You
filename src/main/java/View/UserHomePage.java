package View;

import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class UserHomePage extends HomePage {

    private Update updateWindow;
    private HomePage homePage;
    private InsertVacation insertVacation;
    private DisplayVacations displayVacations;
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
        newStage("Update.fxml", "עדכון פרטים אישיים", updateWindow, 583, 493,controller);
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
        if(tf_origin.getText()==null || tf_destination.getText()==null  || dp_departure.getValue()==null || dp_arrival.getValue() == null ) {
            alert("אופס! אחד או יותר משדות החיפוש ריקים", Alert.AlertType.ERROR);
            return;
        } else {
            int numberOfTickets = 0;
            //valid number (not empty)
            if (!tf_numOfTickets.getText().equals("") && StringUtils.isNumeric(tf_numOfTickets.getText()))
                numberOfTickets = Integer.valueOf(tf_numOfTickets.getText());
            //invalid number (not empty)
            if (!tf_numOfTickets.getText().equals("") && !StringUtils.isNumeric(tf_numOfTickets.getText())) {
                alert("אופס! הערך שהוזן במספר טיסות איננו תקין.", Alert.AlertType.ERROR);
                return;
            }
            //empty, make default 1
            else if (tf_numOfTickets.getText().equals("") || StringUtils.isNumeric(tf_numOfTickets.getText())) {
                //tf_numOfTickets.setText("1");
                numberOfTickets = 1;
                String dateDepart = controller.changeToRightDateFormat(dp_departure.getValue().toString());
                String dateArriv = controller.changeToRightDateFormat(dp_arrival.getValue().toString());
                System.out.println(dateDepart);
                System.out.println(dateArriv);
//                String dateDepart = dp_departure.getValue().toString();
//                String dateArriv = dp_arrival.getValue().toString();
                if(!controller.setMatchesVacations(tf_origin.getText(), tf_destination.getText(), dateDepart, dateArriv, numberOfTickets))
                    newStage("DisplayVacations.fxml", "", displayVacations, 635, 525, controller);
                else
                    alert("מתנצלים אך אין חופשה שתואמת את החיפוש שלך", Alert.AlertType.INFORMATION);
                //tf_numOfTickets.clear();
            }
        }


    }

    public void sellTickets(ActionEvent actionEvent) {

        newStage("InsertVacation.fxml", "יציר חופשה", insertVacation, 760, 430,controller);


    }

    public void logOut(ActionEvent actionEvent){
        stage.close();
        controller.setUserName(null);
        newStage("HomePage.fxml", "כניסת משתמש רשום", homePage, 940, 581,controller);
    }



}
