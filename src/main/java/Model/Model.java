package Model;

import Database.*;
import Database.AvailableVacationsDB;
//import Database.PurchcasedVacationDB;
import Database.UsersDB;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.regex.Pattern;

public class Model extends Observable {

    private UsersDB usersDB;
    public static int vacationID=0;
    private Vacation vacation;
    private AvailableVacationsDB availableVacationsDB;
    private PurchasedVacationsDB purchasedVacationDB;
    private PendingVacationsDB pendingVacationsDB;
    private ConfirmedSaleVacationsDB confirmedSaleVacationsDB;
    private AllVacationsDB allVacationsDB;


    //public enum errorType {PASSWORD_USERS_NOT_MATCH, PASSWORDS_NOT_MATCH, USER_NOT_EXIST}

    /**
     * Constructor for class Model
     * The constructor create a new database with the name "Vacation4U"
     * and create a new table by the name "Users"
     */
    public Model() {
        this.usersDB = new UsersDB("Vacation4U");
        usersDB.connect("Vacation4U");
        usersDB.createTable();

        this.availableVacationsDB = new AvailableVacationsDB("Vacation4U");
        availableVacationsDB.connect("Vacation4U");
        availableVacationsDB.createNewTable();

        this.purchasedVacationDB = new PurchasedVacationsDB("vacation4U");
        purchasedVacationDB.connect("Vacation4U");
        purchasedVacationDB.createNewTable();

        this.pendingVacationsDB = new PendingVacationsDB("Vacation4U");
        pendingVacationsDB.connect("Vacation4U");
        pendingVacationsDB.createNewTable();

        this.confirmedSaleVacationsDB = new ConfirmedSaleVacationsDB("Vacation4U");
        confirmedSaleVacationsDB.connect("Vacation4U");
        confirmedSaleVacationsDB.createNewTable();
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
    public String insert(String userName, String password, String confirmPassword,  String firstName, String lastName, String birthday, String address, String email, String creditCardNumber, String expirationTime,String CSV) {
        String data = userName  + "," + password + "," + firstName + "," + lastName + "," + birthday + "," + address + "," + email + "," + creditCardNumber + "," + expirationTime + "," + CSV;

        // Checking if the user name already exist in the data base
        if (readUsers(userName, true) != null){
            return "שם המשתמש שהזנת כבר קיים";
        }

        // Checking that both password text fields are equal
        else if (!password.equals(confirmPassword)){
            return "הסיסמאות אינן תואמות";
        }
        else if(!isValidEmail(email))
            return "האימייל לא בפורמט הנכון";
        else if(!isValidCreditCardNumber(creditCardNumber))
            return "מספר כרטיס אשראי לא תקין, אנא הזן מספר בן 16 ספרות";
        else if(!isValidCSVNumber(CSV))
            return "מספר CSV לא תקין, אנא הזן מספר בן 3 ספרות";
        else{
            usersDB.insertIntoTable(data);
            return "התחברת בהצלחה";
        }
    }

    /**
     *
     * @param CSV
     * @return true is CSV a valid CSV number. In a length of 3 and contain only digits
     */
    private boolean isValidCSVNumber(String CSV) {
        return CSV.length() == 3 && StringUtils.isNumeric(CSV);
    }

    /**
     *
     * @param email
     * @return true if @param email is in the right format
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    /**
     *
     * @param creditCardNumber
     * @return true is creditCardNumber a valid CSV number. In a length of 16 and contain only digits
     */
    private boolean isValidCreditCardNumber(String creditCardNumber){
        return creditCardNumber.length() == 16 && StringUtils.isNumeric(creditCardNumber);
    }

    /**
     * This method search and return the row in the database with the same user name as @param userName if exist
     * if doesn't exist - alert message shows up
     * @param userName
     * @return
     */
    public String readUsers(String userName, Boolean isInsert) {
        if (usersDB.read("Users", userName) != null){
            return usersDB.read("Users", userName);
        }
        else if (!isInsert){
            alert("שם משתמש לא קיים במערכת", Alert.AlertType.ERROR);
        }
        return null;
    }

    /**
     * This method updateUser the database with the given @param data
     * @param userName
     * @param password
     * @param confirmPassword
     * @param firstName
     * @param lastName
     * @param birthday
     * @param address
     */
    public String updateUser(String oldUserName, String userName, String password, String confirmPassword,  String firstName, String lastName, String birthday, String address, String email, String creditCardNumber, String expirationTime,String CSV) {
        String data = userName  + "," + password + "," + firstName + "," + lastName + "," + birthday + "," + address + "," + email + "," + creditCardNumber + "," + expirationTime + "," + CSV;
        // Checking that both password text fields are equal
        if(!password.equals(confirmPassword)){
            return "הסיסמאות אינן תואמות";
        }
        else if(!isValidEmail(email))
            return "האימייל לא בפורמט הנכון";
        else if(!isValidCreditCardNumber(creditCardNumber))
            return "מספר כרטיס אשראי לא תקין, אנא הזן מספר בן 16 ספרות";
        else if(!isValidCSVNumber(CSV))
            return "מספר CSV לא תקין, אנא הזן מספר בן 3 ספרות";
        else{
            usersDB.updateDatabase(data,oldUserName);
            return "פרטי החשבון עודכנו בהצלחה";
        }

    }

    /**
     * This method deleteUser a row from the database where user name is equal to @param userName
     * @param userName
     */
    public void deleteUser(String userName) {
        usersDB.deleteFromTable( userName);
        alert("החשבון נמחק בהצלחה", Alert.AlertType.INFORMATION);
    }

    public void deleteAvailableVacation(int vacationId){
        availableVacationsDB.deleteFromTable(vacationId);
    }



    public String signIn(String userName, String password) {
        String details = readUsers(userName,false);
        boolean isLegal = true;
        if (details != null){
            String UserDetails = usersDB.read("Users", userName);
            String [] detailsArr = UserDetails.split(",");
            if (!password.equals(detailsArr[1])) {
                return "הסיסמאות אינן תואמות";
            }
            else{
               return userName;

            }
        }
        return userName;
    }

    public void insertPendingVacation(int vacationId,String seller, String buyer ){
        try{
            pendingVacationsDB.insertVacation(vacationId, seller,  buyer);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Vacation> readPendingVacations(String sellerUserName){
        ArrayList<Vacation> pendingVacations = new ArrayList<>();
        pendingVacations = pendingVacationsDB.readPendingVacation(sellerUserName);
        return pendingVacations;
    }

    public String readPendingVacationBuyer(int vacationId){
        return pendingVacationsDB.readPendingVacationBuyer(vacationId);
    }

    public void deletePendingVacation(int vacationID){
        pendingVacationsDB.deleteVacation(vacationID);
    }

    public void insertConfirmedVacation(int vacationId,String seller, String buyer,String origin, String destination, int price, String dateOfDeparture, String dateOfArrival ){
        try{
            confirmedSaleVacationsDB.insertVacation(vacationId, seller,  buyer, origin,destination, price, dateOfDeparture,  dateOfArrival);
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }

    public ArrayList<String> readConfirmedVacations(String buyerUserName){
        ArrayList<String> confirmedVacations = new ArrayList<>();
        confirmedVacations = confirmedSaleVacationsDB.readConfirmedVacations(buyerUserName);
        return confirmedVacations;
    }

    public void deleteConfirmedVacation(int vacationID){
        confirmedSaleVacationsDB.deleteVacation(vacationID);
    }

    private void insertVacation(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice){
        vacationID++;
        Vacation vacation = new Vacation(vacationID, origin,  destination,  price,  destinationAirport,  dateOfDeparture,  dateOfArrival,  airlineCompany,  numOfTickets,  baggage,  ticketsType,  vacationStyle,  seller, originalPrice);
        try {
            availableVacationsDB.insertVacation( vacation, vacationID);
            allVacationsDB.insertVacation(vacation, vacationID);
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            //inform controller something is wrong

            //check in GUI that all values aren't null ,don't handle this here
        }
    }


    private void insertAvailableVacation(int vactionId,String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice){

        Vacation vacation = new Vacation(vactionId, origin,  destination,  price,  destinationAirport,  dateOfDeparture,  dateOfArrival,  airlineCompany,  numOfTickets,  baggage,  ticketsType,  vacationStyle,  seller, originalPrice);
        try {
            availableVacationsDB.insertVacation( vacation, vacationID);
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
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
    public ArrayList<Vacation> getVacations(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller,int OriginalPrice){
        ArrayList<Vacation> matchesVacations = new ArrayList<Vacation>();
        Vacation vacation = new Vacation(origin,  destination,  price,  destinationAirport,  dateOfDeparture,  dateOfArrival,  airlineCompany,  numOfTickets,  baggage,  ticketsType,  vacationStyle,  seller, OriginalPrice);
        matchesVacations = availableVacationsDB.readVacation( vacation);
        return matchesVacations;
    }

    public ArrayList<Vacation> getMatchesVacations(String origin, String destination, String dateOfDeparture,String dateOfArrival,int numOfTickets){
        ArrayList<Vacation> matchesVacations = new ArrayList<Vacation>();
        Vacation vacation = new Vacation(origin,  destination,  dateOfDeparture,  dateOfArrival,  numOfTickets);
        matchesVacations = availableVacationsDB.readMatchVacations( vacation);
        return matchesVacations;
    }

    public void insertPurchasedVacation(int vacationId,String date, String time,String  userName, int creditCard, String expirationDate, int csv){
        try{
            purchasedVacationDB.insertVacation( vacationId, date,  time,  userName,  creditCard,  expirationDate,  csv);
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
            //inform controller something is wrong

            //check in GUI that all values aren't null ,don't handle this here
        }
    }

    public int getVacationID() {
        return vacationID;
    }

    private void alert(String messageText, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setContentText(messageText);
        alert.showAndWait();
        alert.close();
    }

}
