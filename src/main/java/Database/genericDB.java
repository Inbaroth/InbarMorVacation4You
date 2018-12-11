package Database;

import Model.Vacation;

import java.sql.*;
import java.util.ArrayList;

public class genericDB {

    protected String DBName;

    public genericDB(String DBName) {
        this.DBName = DBName;
    }

    /**
     * This method create if doesn't exist a new database by the name which equal to the databaseName field.
     */
    public void connect(String databaseName) {
        Connection connection = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName + ".db");
            connection.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }


    public void deleteFromTable (String tableName,String primaryKey, String userName){
        String deleteStatement = "DELETE FROM "+tableName+" WHERE " +primaryKey+" = ?";

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



    public ArrayList<Vacation> getVacationsBasedOnQuery(String url, String query){
        ArrayList<Vacation> vacations = new ArrayList<Vacation>();
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // loop through the result set
            while (rs.next()) {
                //creating vacation objects only so we could display them, there are not going to be a real availableVacation obects
                Vacation vacation = new Vacation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14));
                vacations.add(vacation);
                vacation = null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vacations;
    }
}
