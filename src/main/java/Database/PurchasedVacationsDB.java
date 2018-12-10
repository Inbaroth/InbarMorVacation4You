package Database;

import Model.Vacation;

import java.sql.*;

public class PurchasedVacationsDB extends genericDB{


    public PurchasedVacationsDB(String databaseName) { super(databaseName); }

    /**
     * Create a new table in the test database
     *
     */
    public void createNewTable() {
        // SQLite connection string
        String url ="jdbc:sqlite:" + DBName + ".db";
        // SQL statement for creating a new table
        String createStatement = "CREATE TABLE IF NOT EXISTS PurchasedVacations (\n"
                + "	VacationId integer PRIMARY KEY,\n"
                + "	DateOfPurchase text NOT NULL,\n"
                + " TimeOfPurchase text NOT NULL,\n"
                + " UserName text NOT NULL,\n"
                + "	CreditCardNumber integer,\n"
                + "	ExpirationDate text,\n"
                + "CSV integer NOT NULL\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void insertVacation( int vacationId,String date, String time,String  userName, int creditCard, String expirationDate, int csv) throws SQLException {
        String insertStatement = "INSERT INTO PurchcasedVacations (VacationId,DateOfPurchase,TimeOfPurchase,UserName,CreditCardNumber,ExpirationDate,CSV) VAlUES (?,?,?,?,?,?,?)";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            pstmt.setInt(1, vacationId);
            pstmt.setString(2, date);
            pstmt.setString(3, time);
            pstmt.setString(4, userName);
            pstmt.setInt(5, creditCard);
            pstmt.setString(6, expirationDate);
            pstmt.setInt(7, csv);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw (new SQLException(e));
        }
    }
}
