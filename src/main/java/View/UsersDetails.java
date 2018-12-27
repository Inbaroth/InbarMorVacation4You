package View;

import Controller.Controller;
import Model.User;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class UsersDetails extends HomePage implements Observer {


    private Controller controller;
    private Stage stage;
    private User user;

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

    public void setUserDetails(User user) {
        this.user = user;
        splitToFields();
    }

    private void splitToFields(){
        userName.setText(user.getUserName());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        String [] date = user.getBirthday().split("/");
        day.setText(date[0]);
        month.setText(date[1]);
        year.setText(date[2]);
        address.setText(user.getAddress());
        email.setText(user.getEmail());


    }


    public void loadPicture(){

    }

    public void close(){
        stage.close();
    }


}
