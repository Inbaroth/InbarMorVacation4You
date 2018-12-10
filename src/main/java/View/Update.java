package View;

import Controller.Controller;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class Update extends View implements Observer {

    private Controller controller;
    private Stage stage;
    private String userDetails;
    private String [] userDetailsSplited;

    public javafx.scene.control.TextField userName;
    public javafx.scene.control.TextField firstName;
    public javafx.scene.control.TextField lastName;
    public javafx.scene.control.TextField password;
    public javafx.scene.control.TextField passwordReplay;
    public javafx.scene.control.TextField address;
    public javafx.scene.control.ComboBox day;
    public javafx.scene.control.ComboBox month;
    public javafx.scene.control.ComboBox year;

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
        userName.setText(userDetailsSplited[0]);
        firstName.setText(userDetailsSplited[2]);
        lastName.setText(userDetailsSplited[3]);
        password.setText(userDetailsSplited[1]);
        passwordReplay.setText(userDetailsSplited[1]);
        address.setText(userDetailsSplited[5]);
        String [] date = userDetailsSplited[4].split("/");
        day.setValue(date[0]);
        month.setValue(date[1]);
        year.setValue(date[2]);
    }

    public void confirm (){
        String newUserName = userName.getText();
        String newPassword = password.getText();
        String newPasswordReplay = passwordReplay.getText();
        String newFirstName = firstName.getText();
        String newLastName = lastName.getText();
        String newBirthday = getBirthday();
        String newAddress = address.getText();
        String data = userDetailsSplited[0] + "," + newPassword + "," + newPasswordReplay + "," + newFirstName + "," + newLastName + "," + newBirthday + "," + newAddress;
        controller.updateDB(userDetailsSplited[0],newUserName,newPassword ,newPasswordReplay,newFirstName , newLastName , newBirthday,newAddress);
        stage.close();
    }

    private String getBirthday(){
        String newDay = (String) day.getValue();
        String newMonth = (String) month.getValue();
        String newYear = (String) year.getValue();
        return newDay  + "/" + newMonth + "/" + newYear;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

}
