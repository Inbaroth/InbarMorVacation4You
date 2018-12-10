package View;

import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.net.URISyntaxException;
import java.util.Observer;

public class HomePage extends View implements Observer {
    private Controller controller;

    public javafx.scene.control.Button bth_createUser;
    public javafx.scene.control.Button btn_signIn;
    public ImageView iv_firstHotVacation;
    public ImageView iv_secondHotVacation;

    private Insert insertWindow;
    private SignIn signInWindow;

    private Stage primaryStage;

    public void setController(Controller controller, Stage primaryStage) {
        this.controller = controller;
        this.primaryStage = primaryStage;


        //check at maze how I created an image in it
        setImage();
    }
    public void create(ActionEvent actionEvent) {
        newStage("insert.fxml", "", insertWindow, 600, 466);
    }

    public void signIn(ActionEvent actionEvent){
        newStage("SignIn.fxml", "כניסת משתמש רשום", signInWindow, 432, 383 );
    }

    public void setImage() {
//        try {
//            //Image img1 = new Image(getClass().getResource("/resources/maldivies.jpg").toURI().toString());
//            Image img1 = new Image(getClass().getResource("resources/maldivies.jpg").toURI().toString());
//            iv_firstHotVacation.setImage(img1);
//            //iv_firstHotVacation = new ImageView(img1);
//            //Image  img2 = new Image(getClass().getResource("/resources/newYork.jpg").toURI().toString());
//           // iv_secondHotVacation = new ImageView(img2);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }

    }
}
