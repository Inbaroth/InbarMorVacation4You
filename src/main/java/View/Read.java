package View;

import Controller.Controller;
import Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observer;

public class Read extends HomePage implements Observer {

    private Controller controller;
    private Stage stage;
    private User user;
    private UsersDetails usersDetails;

    public javafx.scene.control.TextField txtfld_userinput;

    /**
     *
     * @param controller
     * @param stage
     */
    public  void setController(Controller controller, Stage stage){
        this.controller = controller;
        this.stage = stage;
    }

    /**
     * This method search a row in the data base where the primary key is equal to the user input
     * if a row is founded, a new window with the user details is shows up
     */
    public void confirm (){
        String userName = String.valueOf(txtfld_userinput.getText());

        if (txtfld_userinput.getText() == null || txtfld_userinput.getText().trim().isEmpty()) {
            controller.alert("אנא בחר שם משתמש לחיפוש");
        }

        user = controller.readUsers(userName,false);

        if (user != null) {
            stage.close();

            /*FXMLLoader fxmlLoader = new
                    FXMLLoader(getClass().getResource("usersDetails.fxml"));*/
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = null;
            try {
                //root = (Parent) fxmlLoader.load(getClass().getResource("usersDetails.fxml").openStream());
                root = fxmlLoader.load(getClass().getResource("/" + "usersDetails.fxml").openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage newStage = new Stage();
            //set what you want on your scene
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Welcome!");
            Scene scene = new Scene(root, 600, 400);
            newStage.setScene(scene);
            newStage.setResizable(false);
            SetStageCloseEvent(newStage);
            newStage.show();
            usersDetails = fxmlLoader.getController();
            usersDetails.setController(controller, newStage);
            controller.addObserver(usersDetails);
            usersDetails.setUserDetails(user);

        }
    }
    public void cancel(){
        stage.close();
    }

}
