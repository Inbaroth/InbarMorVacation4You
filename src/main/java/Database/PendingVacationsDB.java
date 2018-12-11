package Database;

import Model.Vacation;

import java.sql.*;
import java.util.ArrayList;

public class PendingVacationsDB extends genericDB {


    public PendingVacationsDB(String databaseName) {
        super(databaseName);
    }

    /**
     * Create a new table in the test database
     *
     */
    public void createNewTable() {
        // SQLite connection string
        String url ="jdbc:sqlite:" + DBName + ".db";
        // SQL statement for creating a new table
        String createStatement = "CREATE TABLE IF NOT EXISTS PendingVacations (\n"
                + "	VacationId integer PRIMARY KEY,\n"
                + "	sellerUserName text NOT NULL,\n"
                + " buyerUserName text NOT NULL\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void insertVacation(int vacationId,String seller, String buyer) throws SQLException {
        String insertStatement = "INSERT INTO PendingVacations (VacationId,sellerUserName,buyerUserName) VAlUES (?,?,?)";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            pstmt.setInt(1, vacationId);
            pstmt.setString(2, seller);
            pstmt.setString(3, buyer);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw (new SQLException(e));
        }
    }


    public void deleteVacation(int vacationId){
        String deleteStatement = "DELETE FROM PendingVacations WHERE vacationId = ?";
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
     * list of all vacations the seller need to confirm or reject
     * vacation format(String): VacationId,Origin,Destionation,Price,DateOfDeparture,DateOfArrival
     * @param sellerUserName
     * @return
     */
    public ArrayList<String> readPendingVacation(String sellerUserName){
        ArrayList<String> vacationsToConfirm = new ArrayList<String>();
        String sql = "SELECT VacationId,Origin,Destionation,Price,DateOfDeparture,DateOfArrival FROM AvailableVacationas WHERE SellerUserName IN (SELECT SellerUserName FROM PendingVacations  WHERE SellerUserName=AvailableVacationas.SellerUserName AND PendingVacations.vacationId =AvailableVacationas.VacationId )";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                String str=rs.getInt("VacationId")+","+ rs.getString("Origin") +"," + rs.getString("Destination") + ","+rs.getInt("Price") +","+ rs.getString("DateOfDeparture") +","+ rs.getString("DateOfArrival");
                vacationsToConfirm.add(str);
                str = "";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vacationsToConfirm;
    }


    public String readPendingVacationBuyer(int vacationId){
        String buyer="";
        String sql = "SELECT buyerUserName FROM PendingVacations  WHERE PendingVacations.vacationId = ' " +vacationId+"'";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
               buyer=rs.getString(3);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return buyer;
    }



}
