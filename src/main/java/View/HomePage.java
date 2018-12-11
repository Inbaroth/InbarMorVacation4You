package View;

import Controller.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class HomePage implements Observer {
    private Controller controller;

    public javafx.scene.control.Button bth_createUser;
    public javafx.scene.control.Button btn_signIn;
    public ImageView iv_firstHotVacation;
    public ImageView iv_secondHotVacation;

    public javafx.scene.control.TextField tf_origin;
    public javafx.scene.control.TextField tf_destination;
    public javafx.scene.control.DatePicker dp_arrival;
    public javafx.scene.control.DatePicker dp_departure;
    public javafx.scene.control.TextField tf_numOfTickets;


    private Insert insertWindow;
    private SignIn signInWindow;
    private UserHomePage userHomeWindow;
    private Payment payment;

    public static Stage primaryStage;
    private Update updateWindow;
    private DisplayVacations displayVacations;

    public void setController(Controller controller, Stage primaryStage) {
        this.controller = controller;
        this.primaryStage = primaryStage;
        //check at maze how I created an image in it
            setImage();
    }
    public void create(ActionEvent actionEvent) {
        newStage("insert.fxml", "", insertWindow, 721, 619,controller);
    }

    public void signIn(ActionEvent actionEvent){
        newStage("SignIn.fxml", "כניסת משתמש רשום", signInWindow, 432, 383 , controller);
    }

    public void setImage()  {
 /*   try {
        Image img1 = new Image(getClass().getResource("/newYork.jpg").toURI().toString());
        iv_firstHotVacation.setImage(img1);
        Image img2 = new Image(getClass().getResource("/maldives.jpg").toURI().toString());
        iv_secondHotVacation.setImage(img2);
    }catch (URISyntaxException e){
        System.out.println(e.getReason() + "," + e.getMessage());
    }*/

    }

    /**
     * creates a new window, based on given details and shows it
     * @param fxmlName - name of the stage fxml file
     * @param title - title of the window
     * @param windowName - name of the java class represents the stage
     * @param width of window
     * @param height of window
     * @param controller - controller of the program, link between the view and model
     */
    protected void newStage(String fxmlName,String title, HomePage windowName, int width, int height, Controller controller){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getResource("/" + fxmlName).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
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
            String userDetails = controller.readUsers(controller.getUserName(),false);
            updateWindow = (Update) windowName;
            updateWindow.setUserDetails(userDetails);
        }

        if (windowName instanceof Payment){
            String userDetails = controller.readUsers(controller.getUserName(),false);
            payment = (Payment) windowName;
            payment.setDetails(userDetails);

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


    public void search(){
        //HERE
        controller.setVac();
        newStage("DisplayVacations.fxml", "", displayVacations,635, 525, controller );
        System.out.println(dp_arrival.getValue().atStartOfDay());
        System.out.println(dp_arrival.getValue());
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
