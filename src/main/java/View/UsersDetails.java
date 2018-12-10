package View;

import Controller.Controller;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class UsersDetails extends View implements Observer {


    private Controller controller;
    private Stage stage;
    private String userDetails;
    private String [] userDetailsSplited;

    public javafx.scene.control.Label userName;
    public javafx.scene.control.Label firstName;
    public javafx.scene.control.Label lastName;
    public javafx.scene.control.Label day;
    public javafx.scene.control.Label month;
    public javafx.scene.control.Label year;
    public javafx.scene.control.Label address;
    public javafx.scene.control.Label email;



    public void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
    }


    @Override
    public void update(Observable o, Object arg) {
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
        splitToFields();
    }

    private void splitToFields(){
        userDetailsSplited = userDetails.split(",");
        userName.setText(userDetailsSplited[0]);
        firstName.setText(userDetailsSplited[2]);
        lastName.setText(userDetailsSplited[3]);
        String [] date = userDetailsSplited[4].split("/");
        day.setText(date[0]);
        month.setText(date[1]);
        year.setText(date[2]);
        address.setText(userDetailsSplited[5]);
        email.setText(userDetailsSplited[6]);


    }


    public void loadPicture(){

    }

    public void close(){
        stage.close();
    }


}
