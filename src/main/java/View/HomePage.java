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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class HomePage implements Observer {
    private Controller controller;

    public javafx.scene.control.Button bth_createUser;
    public javafx.scene.control.Button btn_signIn;
    public ImageView iv_firstHotVacation;
    public ImageView iv_secondHotVacation;

    private Insert insertWindow;
    private SignIn signInWindow;

    private Stage primaryStage;
    private Update updateWindow;

    public void setController(Controller controller, Stage primaryStage) {
        this.controller = controller;
        this.primaryStage = primaryStage;


        //check at maze how I created an image in it
        setImage();
    }
    public void create(ActionEvent actionEvent) {
        newStage("insert.fxml", "", insertWindow, 721, 619);
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

    //create a new stage
    protected void newStage(String fxmlName,String title, HomePage windowName, int width, int height){
        /*FXMLLoader fxmlLoader = new
                FXMLLoader(getClass().getResource(fxmlName));*/
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getResource("/" + fxmlName).openStream());
            //root = (Parent) fxmlLoader.load(getClass().getResource(fxmlName).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        //set what you want on your scene
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setResizable(false);
        SetStageCloseEvent(stage);
        stage.show();
        windowName = fxmlLoader.getController();
        windowName.setController(controller, stage);
        controller.addObserver(windowName);

        if (windowName instanceof Update){
            String userDetails = controller.read(controller.getUserName(),false);
            updateWindow = (Update) windowName;
            updateWindow.setUserDetails(userDetails);
        }
    }


    protected void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("כן");
                ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("חזור");
                alert.setContentText("האם אתה בטוח שברצונך לעצוב?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    // Close program

                    //disable  buttons as needed
                    //btn_update.setDisable(false);
                    //btn_delete.setDisable(false);
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();

                }
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    protected void alert(String messageText, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setContentText(messageText);
        alert.showAndWait();
        alert.close();
    }


}
