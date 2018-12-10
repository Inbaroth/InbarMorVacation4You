package Model;

import Database.AvailableVacationsDB;
import Database.PurchcasedVacationDB;
import Database.UsersDB;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {

    private UsersDB usersDB;
    private AvailableVacationsDB availableVacationsDB;
    private PurchcasedVacationDB purchcasedVacationDB;
    public static int vacationID=0;
    private Vacation vacation;


    //public enum errorType {PASSWORD_USERS_NOT_MATCH, PASSWORDS_NOT_MATCH, USER_NOT_EXIST}

    /**
     * Constructor for class Model
     * The constructor create a new database with the name "Vacation4U"
     * and create a new table by the name "Users"
     */
    public Model() {
        this.usersDB = new UsersDB("Vacation4U");
        usersDB.connect("Users");
        //usersDB.createTable("Users");

        this.availableVacationsDB = new AvailableVacationsDB("AvailableVacations");
       // availableVacationsDB.connect();

    }

    /**
     * This method insert to the database a new row with the given parameters
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     * @param birthday
     * @param address
     * @return true if insert succeeded, otherwise return false
     */
    public int insert(String userName, String password, String confirmPassword,  String firstName, String lastName, String birthday, String address ) {
        String data = userName  + "," + password + "," + firstName + "," + lastName + "," + birthday + "," + address;

        // Checking if the user name already exist in the data base
        if (read(userName, true) != null){
            // 1 symbol notification type: username already exist in the database
            return 1;
        }

        // Checking that both password text fields are equal
        else if (!password.equals(confirmPassword)){
            // 2 symbol notification type: passwords doesn't match
            return 2;
        }
        else{
            usersDB.insertIntoTable("Users", data);
            // 3 symbol notification type: user connected successfully
            return 3;
        }


    }

    /**
     * This method search and return the row in the database with the same user name as @param userName if exist
     * if doesn't exist - alert message shows up
     * @param userName
     * @return
     */
    public String read(String userName, Boolean isInsert) {
        if (usersDB.read("Users", userName) != null){
            return usersDB.read("Users", userName);
        }
        else if (!isInsert){
            alert("שם משתמש לא קיים במערכת", Alert.AlertType.ERROR);
        }
        return null;
    }

    /**
     * This method update the database with the given @param data
     * @param userName
     * @param password
     * @param confirmPassword
     * @param firstName
     * @param lastName
     * @param birthday
     * @param address
     */

    public void update(String oldUserName,String userName, String password, String confirmPassword,  String firstName, String lastName, String birthday, String address) {
        String data = userName  + "," + password + "," + firstName + "," + lastName + "," + birthday + "," + address;
        // Checking that both password text fields are equal
        if(!password.equals(confirmPassword)){
            alert("הסיסמאות אינן תואמות", Alert.AlertType.ERROR);
        }
        else{
            usersDB.updateDatabase("Users", data,oldUserName);
            alert("פרטי החשבון עודכנו בהצלחה", Alert.AlertType.INFORMATION);
        }

    }

    /**
     * This method delete a row from the database where user name is equal to @param userName
     * @param userName
     */
    public void delete(String userName) {
        usersDB.deleteFromTable("Users","user_name" ,userName);
        alert("החשבון נמחק בהצלחה", Alert.AlertType.INFORMATION);
    }

    public void signIn(String userName, String password) {
        String details = read(userName,false);
        boolean isLegal = true;
        if (details != null){
            String UserDetails = usersDB.read("Users", userName);
            String [] detailsArr = UserDetails.split(",");
            if (!password.equals(detailsArr[1])) {
                //alert("הסיסמאות אינן תואמות", Alert.AlertType.ERROR);
                isLegal = false;
                setChanged();
                notifyObservers(isLegal);
                //return isLegal;
            }
            else{
                setChanged();
                notifyObservers(isLegal);
                //return isLegal;

            }
        }
        //return false;
    }

    private void alert(String messageText, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setContentText(messageText);
        alert.showAndWait();
        alert.close();
    }


    private void insertVacation(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller){
        vacationID++;
        Vacation vacation = new Vacation(vacationID, origin,  destination,  price,  destinationAirport,  dateOfDeparture,  dateOfArrival,  airlineCompany,  numOfTickets,  baggage,  ticketsType,  vacationStyle,  seller);
        try {
            availableVacationsDB.insertVacation("AvailableVacations", vacation, vacationID);
        }catch (SQLException e){
            //inform controller something is wrong

            //check in GUI that all values aren't null ,don't handle this here
        }
    }

    /**
     * returns list of all match vacation from DB based on given data (as the parameters in signature)
     * @param origin
     * @param destination
     * @param price
     * @param destinationAirport
     * @param dateOfDeparture
     * @param dateOfArrival
     * @param airlineCompany
     * @param numOfTickets
     * @param baggage
     * @param ticketsType
     * @param vacationStyle
     * @param seller
     * @return
     */
    public ArrayList<Vacation> getVactions(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller){
       ArrayList<Vacation> matchesVacations = new ArrayList<Vacation>();
        Vacation vacation = new Vacation(origin,  destination,  price,  destinationAirport,  dateOfDeparture,  dateOfArrival,  airlineCompany,  numOfTickets,  baggage,  ticketsType,  vacationStyle,  seller);
        matchesVacations = availableVacationsDB.readVacation("AvailableVacations", vacation);
        return matchesVacations;
    }


    public ArrayList<Vacation> getMatchesVacations(String origin, String destination, String dateOfDeparture,String dateOfArrival,int numOfTickets){
        ArrayList<Vacation> matchesVacations = new ArrayList<Vacation>();
        Vacation vacation = new Vacation(origin,  destination,  dateOfDeparture,  dateOfArrival,  numOfTickets);
        matchesVacations = availableVacationsDB.readMatchVacations("AvailableVacations", vacation);
        return matchesVacations;
    }


    public int getVacationID() {
        return vacationID;
    }
}
