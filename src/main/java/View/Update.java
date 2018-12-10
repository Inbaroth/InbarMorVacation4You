package View;

import Controller.Controller;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class Update extends HomePage implements Observer {

    private Controller controller;
    private Stage stage;
    private String userDetails;
    private String [] userDetailsSplited;

    public javafx.scene.control.TextField txtfld_userName;
    public javafx.scene.control.PasswordField txtfld_password;
    public javafx.scene.control.PasswordField txtfld_confirmPassword;
    public javafx.scene.control.TextField txtfld_firstName;
    public javafx.scene.control.TextField txtfld_lastName;
    public javafx.scene.control.TextField txtfld_Address;
    public javafx.scene.control.TextField txtfld_email;
    public javafx.scene.control.TextField txtfld_creditCardNumber;
    public javafx.scene.control.TextField txtfld_CSV;
    public javafx.scene.image.ImageView pictureView;
    public javafx.scene.control.ComboBox combo_box_day;
    public javafx.scene.control.ComboBox combo_box_month;
    public javafx.scene.control.ComboBox combo_box_year;
    public javafx.scene.control.ComboBox combo_box_yearForCredit;
    public javafx.scene.control.ComboBox combo_box_monthForCredit;

    /**
     *
     * @param controller
     * @param stage
     */
    public void setController(Controller controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
        this.userDetails = "";
    }


    /**
     * This method set the userDetails field
     * @param userdetails
     */
    public void setUserDetails(String userdetails) {
        userDetails = userdetails;
        splitToFields();
    }


    /**
     * This method split the data from the database into fields and initials the Text Fields in the
     * updateUser window
     */
    private void splitToFields(){
        userDetailsSplited = userDetails.split(",");
        txtfld_userName.setText(userDetailsSplited[0]);
        txtfld_firstName.setText(userDetailsSplited[2]);
        txtfld_lastName.setText(userDetailsSplited[3]);
        txtfld_password.setText(userDetailsSplited[1]);
        txtfld_confirmPassword.setText(userDetailsSplited[1]);
        txtfld_Address.setText(userDetailsSplited[5]);
        txtfld_email.setText(userDetailsSplited[6]);
        txtfld_creditCardNumber.setText(userDetailsSplited[7]);
        txtfld_CSV.setText(userDetailsSplited[9]);
        String [] date = userDetailsSplited[4].split("/");
        String [] exp = userDetailsSplited[8].split("/");
        combo_box_day.setValue(date[0]);
        combo_box_month.setValue(date[1]);
        combo_box_year.setValue(date[2]);
        combo_box_monthForCredit.setValue(exp[0]);
        combo_box_yearForCredit.setValue(exp[1]);
    }

    /**
     *
     */
    public void confirm (){
        String newUserName = txtfld_userName.getText();
        String newPassword = txtfld_password.getText();
        String newPasswordReplay = txtfld_confirmPassword.getText();
        String newFirstName = txtfld_firstName.getText();
        String newLastName = txtfld_lastName.getText();
        String newBirthday = getBirthday();
        String newAddress = txtfld_Address.getText();
        String newEmail = txtfld_email.getText();
        String newCreditCardNumber = txtfld_creditCardNumber.getText();
        String newExp = getExp();
        String newCSV = txtfld_CSV.getText();
        String data = userDetailsSplited[0] + "," + newPassword + "," + newPasswordReplay + "," + newFirstName + "," + newLastName + "," + newBirthday + "," + newAddress;
        controller.updateDB(userDetailsSplited[0],newUserName,newPassword ,newPasswordReplay,newFirstName , newLastName , newBirthday,newAddress);
        stage.close();
    }

    private String getExp() {
        String newMonth = (String) combo_box_monthForCredit.getValue();
        String newYear = (String) combo_box_yearForCredit.getValue();
        return newMonth + "/" + newYear;
    }

    /**
     *
     * @return
     */
    private String getBirthday(){
        String newDay = (String) combo_box_day.getValue();
        String newMonth = (String) combo_box_month.getValue();
        String newYear = (String) combo_box_year.getValue();
        return newDay  + "/" + newMonth + "/" + newYear;
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

    @Override
    public void update(Observable o, Object arg) {

    }

}
