package Model;

import Database.AvailableVacationsDB;
import Database.UsersDB;
import javafx.scene.control.Alert;
import java.util.Observable;
import java.util.regex.Pattern;

public class Model extends Observable {

    private UsersDB usersDB;
    private AvailableVacationsDB availableVacationsDB;


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
    public String insert(String userName, String password, String confirmPassword,  String firstName, String lastName, String birthday, String address, String email, String creditCardNumber, String expirationTime,String CSV) {
        String data = userName  + "," + password + "," + firstName + "," + lastName + "," + birthday + "," + address + "," + email + "," + creditCardNumber + "," + expirationTime + "," + CSV;

        // Checking if the user name already exist in the data base
        if (read(userName, true) != null){
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

}
