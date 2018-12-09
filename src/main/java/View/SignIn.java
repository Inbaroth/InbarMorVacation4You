package View;

import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class SignIn extends View implements Observer{

    private Controller controller;
    private Stage stage;
    private String userDetails;

    public javafx.scene.control.TextField username;
    public javafx.scene.control.PasswordField password;

    public SignIn() {

    }

    public void setController(Controller controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;

    }


    public void logIn(){

        String userName = username.getText();
        String Password = password.getText();

        //if one or more is empty
        if(userName == null || Password == null || username.getText().trim().isEmpty() || password.getText().trim().isEmpty()){
            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("שגיאה");
            alert.setContentText("שדה אחד או יותר ריקים");
            alert.showAndWait();
            alert.close();*/
            alert("שדה אחד או יותר ריקים", Alert.AlertType.INFORMATION);
        }
        else{
            // read the user name from the data base
            // if doesn't exist showing alert message
            userDetails = controller.read(userName,false);

            //if the password is not correct shows alert massage
            if (userDetails != null) {
                controller.signIn(userName, Password);
                stage.close();
            }


        }

    }

}

