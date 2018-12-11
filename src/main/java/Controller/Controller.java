package Controller;

import Model.Model;
import Model.Vacation;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Controller extends Observable implements Observer {

    private Model model;
    private String currentUserName;

    private ArrayList<Vacation> vacationMatchSearch;



    /**
     * Constructor for the class Controller
     * @param model
     */
    public Controller(Model model) {

        this.model = model;
    }


    //delete after test
    public void setVac(){
        //delete after test
        vacationMatchSearch = new ArrayList<Vacation>();
        vacationMatchSearch.add(new Vacation("newYork", "newYork","newYork","newYork",5));
        vacationMatchSearch.add(new Vacation("newYork", "newYork","newYork","newYork",5));
        vacationMatchSearch.add(new Vacation("newYork", "newYork","newYork","newYork",5));
        vacationMatchSearch.add(new Vacation("newYork", "newYork","newYork","newYork",5));
    }

    /**
     *
     * @param userName
     * @param password
     * @param birthday
     * @param firstName
     * @param lastName
     * @param address
     * This method insert a new row to the data base with the given parameters
     */
    public String insertUser (String userName, String password, String confirmPassword, String birthday, String firstName, String lastName, String address, String email, String creditCardNumber, String expirationTime,String CSC) {
        return model.insert(userName,password,confirmPassword,birthday,firstName,lastName,address,email, creditCardNumber,expirationTime,CSC);
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
    public void deleteUser(String userName){
        model.deleteUser(userName);
    }

    public ArrayList<String> readPendingVacations(String sellerUserName){
        return model.readPendingVacations(sellerUserName);
    }

    public ArrayList<String> readConfirmedVacations(String buyerUserName){
        return model.readConfirmedVacations(buyerUserName);
    }

    public void deletePendingVacation(String vacationID){
        model.deletePendingVacation(Integer.valueOf(vacationID));
    }
    public void insertConfirmedVacation(int vacationId,String seller, String buyer,String origin, String destination, int price, String dateOfDeparture, String dateOfArrival ){
        model.insertConfirmedVacation(vacationId,seller,buyer,origin,destination,price,dateOfDeparture,dateOfArrival);
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

    public String signIn(String userName, String password){
        currentUserName = userName;
        return model.signIn(userName,password);
    }

    public void setMatchesVacations(String origin, String destination, String dateOfDeparture,String dateOfArrival,int numOfTickets){
        vacationMatchSearch = new ArrayList<Vacation>();
        vacationMatchSearch = model.getMatchesVacations(origin,  destination,  dateOfDeparture,  dateOfArrival,  numOfTickets);
    }

    public ArrayList<Vacation> getMatchesVacations(){
       if(vacationMatchSearch!=null)
           return vacationMatchSearch;
       return null;
    }

    public String readPendingVacationBuyer(int VacationId){
      return model.readPendingVacationBuyer(VacationId);
    }

    public String getUserName() {
        return currentUserName;
    }
}
