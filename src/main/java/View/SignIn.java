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

public class SignIn extends HomePage implements Observer{

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
            alert("שדה אחד או יותר ריקים", Alert.AlertType.ERROR);
        }
        else{
            String ans = controller.signIn(userName, Password);
            if (!ans.equals(userName))
                alert(ans,Alert.AlertType.ERROR);
            else {





                stage.close();
            }
        }


    }

}

