package Controller;

import Model.Model;
import javafx.scene.control.Alert;
import java.util.Observable;
import java.util.Observer;

public class Controller extends Observable implements Observer {

    private Model model;
    private String currentUserName;

    /**
     * Constructor for the class Controller
     * @param model
     */
    public Controller(Model model) {
        this.model = model;
    }

    /**
     *
     * @param userName
     * @param password
     * @param birthday
     * @param firstName
     * @param lastName
     * @param address
     * This method insertUser a new row to the data base with the given parameters
     */
    public int insertUser(String userName, String password, String confirmPassword, String birthday, String firstName, String lastName, String address) {
        return model.insertUsers(userName,password,confirmPassword,birthday,firstName,lastName,address);
    }

    /**
     * This method search and return the row in the database which is equal to the given userName
     * @param userName
     * @return the row
     */
    public String readUsers(String userName, Boolean isInsert){
        return model.readUsers(userName,isInsert);
    }

    /**
     *
     * @param userName
     * @param password
     * @param confirmPassword
     * @param birthday
     * @param firstName
     * @param lastName
     * @param address
     */
    public void updateDB(String oldUserName, String userName, String password, String confirmPassword, String birthday, String firstName, String lastName, String address){
        model.updateUser(oldUserName,userName,password,confirmPassword,birthday,firstName,lastName,address);
    }

    /**
     * This method deleteUser a row from the data base where the primary key is equal to @param userName
     * @param userName
     */
    public void delete (String userName){
        model.deleteUser(userName);
    }

    /**
     * This method create an Alert object.
     * This method invoked when the user didn't insertUser an input
     */
    public void alert(String messageText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(messageText);
        alert.showAndWait();
        alert.close();
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o == model){
            setChanged();
            notifyObservers(arg);
        }

    }

    public void signIn(String userName, String password){
        currentUserName = userName;
        model.signIn(userName,password);
    }

    public String getUserName() {
        return currentUserName;
    }
}
