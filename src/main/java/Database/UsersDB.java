package Database;

import Model.User;

import java.sql.*;

public class UsersDB extends genericDB{

    /**
     * Constructor for the class UsersDB
     * @param databaseName
     */
    public UsersDB(String databaseName) {
        super(databaseName);
    }


    /**
     * This method create a new table in the data base by the name tableName
     */
    public void createTable(){
        String createStatement = "CREATE TABLE IF NOT EXISTS Users (\n"
                + "	user_name text PRIMARY KEY,\n"
                + "	password text NOT NULL,\n"
                + " first_name text NOT NULL,\n"
                + " last_name text NOT NULL,\n"
                + "	birthday text,\n"
                + "	address text,\n"
                + "	email text,\n"
                + " profilePicture text NOT NULL\n"
                + ");";

        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * This method insert a new row to Users table with the given data
     * @param user  data of the new row which need to be added
     */
    public void insertIntoTable(User user){

        String insertStatement = "INSERT INTO Users (user_name, password, first_name, last_name, birthday, address, email, profilePicture, credit_card_number, expiration_time, CSV) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        String url = "jdbc:sqlite:" + DBName + ".db";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            pstmt.setString(1,user.getUserName()); // user name
            pstmt.setString(2,user.getPassword()); // password
            pstmt.setString(3,user.getFirstName()); // first name
            pstmt.setString(4,user.getLastName()); // last name
            pstmt.setString(5,user.getBirthday()); // birthday
            pstmt.setString(6,user.getAddress()); // address
            pstmt.setString(7,user.getEmail()); // email
            pstmt.setString(8,"picture"); // picture
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method search and return the row in the database which is equal to the given userName
     * @param userName - user name to search by
     * @return the founded row
     */
    public String read (String userName){

        String selectQuery = "SELECT * FROM users WHERE user_name = ?";

        String url = "jdbc:sqlite:" + DBName + ".db";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {

            // set the value
            pstmt.setString(1,userName);
            ResultSet rs  = pstmt.executeQuery();

            while (rs.next()) {
                String res = rs.getString("user_name") + "," +
                        rs.getString("password") + "," +
                        rs.getString("first_name") + "," +
                        rs.getString("last_name") + "," +
                        rs.getString("birthday") + "," +
                        rs.getString("address") + "," +
                        rs.getString("email") + "," +
                        rs.getString("profilePicture");
                return res;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    /**
     * This method update the row in the data base where the user name is equal to the given user name in the
     * data string
     * @param data - all the parameters needed to be updated
     */
    public void updateDatabase(String data, String userName) {
        // add fields!!!!
        String[] values = data.split(",");
        String updatetatement = "UPDATE Users SET user_name = ?,"
                + "password = ? ,"
                + "first_name = ? ,"
                + "last_name = ? ,"
                + "birthday = ? ,"
                + "address = ? ,"
                + "email = ? ,"
                + "profilePicture= ?"
                + "WHERE user_name = ?";

        String url = "jdbc:sqlite:" + DBName + ".db";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(updatetatement)) {

            // set the corresponding param
            pstmt.setString(1, values[0]); // user name
            pstmt.setString(2, values[1]); // password
            pstmt.setString(3, values[2]); // first name
            pstmt.setString(4, values[3]); // last name
            pstmt.setString(5, values[4]); // birthday
            pstmt.setString(6,values[5]); // address
            pstmt.setString(7,values[6]); // email
            pstmt.setString(8,"picture"); // picture
            pstmt.setString(12, userName); // user name - primary key
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method deleteUser a row from the data base where the user name is equal to given userName param
     * @param userName
     */
    public void deleteFromTable ( String userName){
        String deleteStatement = "DELETE FROM Users WHERE user_name = ?";

        String url = "jdbc:sqlite:" + DBName + ".db";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            // set the corresponding param
            pstmt.setString(1, userName);
            // execute the deleteUser statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}

