package Database;

import java.sql.*;
import java.util.ArrayList;

public class ConfirmedSaleVacationsDB extends genericDB {


    public ConfirmedSaleVacationsDB(String databaseName) { super(databaseName); }

    /**
     * Create a new table in the test database
     *
     */
    public void createNewTable() {
        // SQLite connection string
        String url ="jdbc:sqlite:" + DBName + ".db";
        // SQL statement for creating a new table
        String createStatement = "CREATE TABLE IF NOT EXISTS ConfirmedSaleVacations (\n"
                + "	VacationId integer PRIMARY KEY,\n"
                + "	sellerUserName text NOT NULL,\n"
                + "	buyerUserName text NOT NULL,\n"
                + "	Origin text NOT NULL,\n"
                + "	Destination text NOT NULL,\n"
                + "	Price integer NOT NULL,\n"
                + "	DateOfDeparture text NOT NULL,\n"
                + " DateOfArrival text NOT NULL\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void insertVacation( int vacationId,String seller, String buyer, String origin, String destination, int price, String dateOfDeparture, String dateOfArrival) throws SQLException {
        String insertStatement = "INSERT INTO ConfirmedSaleVacations (VacationId,sellerUserName,buyerUserName,Origin,Destination,Price,DateOfDeparture,DateOfArrival) VAlUES (?,?,?,?,?,?,?,?)";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            pstmt.setInt(1, vacationId);
            pstmt.setString(2, seller);
            pstmt.setString(3, buyer);
            pstmt.setString(4, origin);
            pstmt.setString(5, destination);
            pstmt.setInt(6, price);
            pstmt.setString(7, dateOfDeparture);
            pstmt.setString(8, dateOfArrival);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw (new SQLException(e));
        }
    }



    public void deleteVacation(int vacationId){
        String deleteStatement = "DELETE FROM ConfirmedSaleVacations WHERE vacationId = ?";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            // set the corresponding param
            pstmt.setInt(1, vacationId);
            // execute the deleteUser statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * list of all vacations the buyer got confirm on and need to pay for
     * vacation format(String): VacationId,Origin,Destionation,Price,DateOfDeparture,DateOfArrival
     * @param buyerUserName
     * @return
     */
    public ArrayList<String> readConfirmedVacation(String buyerUserName){
        ArrayList<String> vacationsToPay = new ArrayList<String>();
        String sql = "SELECT vacationId,Origin,Destination,Price,DateOfDeparture,DateOfArrival FROM ConfirmedSaleVacations WHERE buyerUserName='" +buyerUserName+ "'";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection connect = DriverManager.getConnection(url);
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                String str=rs.getInt("VacationId")+","+ rs.getString("Origin") +"," + rs.getString("Destination") + ","+rs.getInt("Price") +","+ rs.getString("DateOfDeparture") +","+ rs.getString("DateOfArrival");
                vacationsToPay.add(str);
                str = "";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vacationsToPay;
    }






}
