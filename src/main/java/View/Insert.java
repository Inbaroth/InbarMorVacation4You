package View;

import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
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
    public javafx.scene.control.TextField txtfld_email;
    public javafx.scene.control.TextField txtfld_creditCardNumber;
    public javafx.scene.control.TextField txtfld_CSC;
    public javafx.scene.image.ImageView pictureView;
    public javafx.scene.control.ComboBox combo_box_day;
    public javafx.scene.control.ComboBox combo_box_month;
    public javafx.scene.control.ComboBox combo_box_year;
    public javafx.scene.control.ComboBox combo_box_yearForCredit;
    public javafx.scene.control.ComboBox combo_box_monthForCredit;
    //</editor-fold>


    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
    }

    public void submit(ActionEvent actionEvent) {
        String userName = txtfld_userName.getText();
        String email = txtfld_email.getText();
        String password = txtfld_password.getText();
        String confirmPassword = txtfld_confirmPassword.getText();
        String firstName = txtfld_firstName.getText();
        String lastName = txtfld_lastName.getText();
        String address = txtfld_Address.getText();
        String creditCardNumber = txtfld_creditCardNumber.getText();
        String CSC = txtfld_CSC.getText();


        if (!validation()){
            alert("שדה אחד או יותר ריקים", Alert.AlertType.INFORMATION);
        }
        else{
            int ans = controller.insert(userName,password,confirmPassword,firstName,lastName,getBirthday(),address,email,creditCardNumber,getExpirationTime(),CSC);
            if (ans == 1)
                alert("שם המשתמש שהזנת כבר קיים", Alert.AlertType.ERROR);
            if (ans == 2)
                alert("הסיסמאות אינן תואמות", Alert.AlertType.ERROR);
            if (ans == 3)
                alert("האימייל לא בפורמט הנכון", Alert.AlertType.ERROR);
            if (ans == 4){
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
        if (txtfld_Address.getText() == null || txtfld_Address.getText().trim().isEmpty())
            return false;
        if (txtfld_email.getText() == null || txtfld_email.getText().trim().isEmpty())
            return false;
        if (txtfld_creditCardNumber.getText() == null || txtfld_creditCardNumber.getText().trim().isEmpty())
            return false;
        if (txtfld_CSC.getText() == null || txtfld_CSC.getText().trim().isEmpty())
            return false;
        return true;
    }

    /**
     * get the user birthday
     * @return the value of the combo_box_day, combo_box_month, combo_box_year in the format: DD/MM/YYYY
     */
    private String getBirthday (){
        String day = (String) combo_box_day.getValue();
        String month = (String) combo_box_month.getValue();
        String year = (String) combo_box_year.getValue();
        return day  + "/" + month + "/" + year;
    }

    /**
     * get the user expiration credit card time
     * @return the value of the combo_box_monthForCredit, combo_box_yearForCredit in the format: MM/YYYY
     */
    private String getExpirationTime(){
        String month = (String) combo_box_monthForCredit.getValue();
        String year = (String) combo_box_yearForCredit.getValue();
        return month + "/" + year;
    }

    /**
     * if the user clicked on cancel, close the program
     * @param actionEvent
     */
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

    public void loadPicture(ActionEvent actionEvent) {
    }
}
