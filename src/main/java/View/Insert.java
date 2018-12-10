package View;

import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class Insert extends View implements Observer {

    private Controller controller;
    private Stage stage;

    //<editor-fold desc="Text Fields">
    public javafx.scene.control.TextField txtfld_userName;
    public javafx.scene.control.PasswordField txtfld_password;
    public javafx.scene.control.PasswordField txtfld_confirmPassword;
    public javafx.scene.control.TextField txtfld_firstName;
    public javafx.scene.control.TextField txtfld_lastName;
    public javafx.scene.control.TextField txtfld_Address;
    public javafx.scene.control.ComboBox combo_box_day;
    public javafx.scene.control.ComboBox combo_box_month;
    public javafx.scene.control.ComboBox combo_box_year;
    //</editor-fold>


    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
    }

    public void submit(ActionEvent actionEvent) {
        String userName = txtfld_userName.getText();
        String password = txtfld_password.getText();
        String confirmPassword = txtfld_confirmPassword.getText();
        String firstName = txtfld_firstName.getText();
        String lastName = txtfld_lastName.getText();
        String address = txtfld_Address.getText();

        if (!validation()){
            alert("שדה אחד או יותר ריקים", Alert.AlertType.INFORMATION);
        }
        else{
            int ans = controller.insertUser(userName,password,confirmPassword,firstName,lastName,getBirthday(),address);
            if (ans == 1)
                alert("שם המשתמש שהזנת כבר קיים", Alert.AlertType.ERROR);
            if (ans == 2)
                alert("הסיסמאות אינן תואמות", Alert.AlertType.ERROR);
            if (ans == 3){
                alert("התחברת בהצלחה", Alert.AlertType.INFORMATION);
                stage.close();
            }

        }

    }

    /**
     * This method checks if the user filled all the Text Fields
     * @return true if the user filled all the Text Fields, otherwise return false
     */
    private boolean validation() {
        if (txtfld_userName.getText() == null || txtfld_userName.getText().trim().isEmpty())
            return false;
        if (txtfld_password.getText() == null || txtfld_password.getText().trim().isEmpty())
            return false;
        if (txtfld_confirmPassword.getText() == null || txtfld_confirmPassword.getText().trim().isEmpty())
            return false;
        if (txtfld_firstName.getText() == null || txtfld_firstName.getText().trim().isEmpty())
            return false;
        if (txtfld_lastName.getText() == null || txtfld_lastName.getText().trim().isEmpty())
            return false;
        if (txtfld_Address.getText() == null || txtfld_Address.getText().trim().isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }

    private String getBirthday (){
        String day = (String) combo_box_day.getValue();
        String month = (String) combo_box_month.getValue();
        String year = (String) combo_box_year.getValue();
        return day  + "/" + month + "/" + year;
    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == controller){
            stage.close();
        }
    }

    public Stage getStage() {
        return stage;
    }
}
