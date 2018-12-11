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
    public ArrayList<Vacation> readPendingVacation(String sellerUserName){
        ArrayList<Vacation> vacations = new ArrayList<Vacation>();
        String sql = "SELECT VacationId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany, NumberOfTickets,Baggage, TicketsType,VacationStyle," +
                "SellerUserName,OriginalPrice, FROM AvailableVacations WHERE SellerUserName " +
                "IN (SELECT SellerUserName FROM PendingVacations  WHERE " +
                "SellerUserName='" +sellerUserName+"'AND PendingVacations.vacationId =AvailableVacations.VacationId )";
        String query = "SELECT VacationId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival," +
                "AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,sellerUserName," +
                "OriginalPrice FROM AllVacations where SellerUserName IN (SELECT sellerUserName FROM PendingVacations Where sellerUserName=?)";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query)){
                stmt.setString(1,sellerUserName);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    //creating vacation objects only so we could display them, there are not going to be a real availableVacation obects
                    Vacation vacation = new Vacation(rs.getInt("VacationId"),
                            rs.getString("Origin"),
                            rs.getString("Destination"),
                            rs.getInt("Price"),
                            rs.getString("DestinationAirport"),
                            rs.getString("DateOfDeparture"),
                            rs.getString("DateOfArrival"),
                            rs.getString("AirlineCompany"),
                            rs.getInt("NumberOfTickets"),
                            rs.getString("Baggage"),
                            rs.getString("TicketsType"),
                            rs.getString("VacationStyle"),
                            rs.getString("sellerUserName"),
                            rs.getInt("NumberOfTickets"));
                    vacations.add(vacation);
                    vacation = null;
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //vacations = getVacationsBasedOnQuery(url, sql);
        return vacations;
    }


    public String readPendingVacationBuyer(int vacationId){
        String buyer="";
        String sql = "SELECT buyerUserName FROM PendingVacations WHERE VacationId =?";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,vacationId);
            ResultSet rs = stmt.executeQuery();
             while (rs.next()){
                     // loop through the result set
                     buyer = rs.getString("buyerUserName");
                 }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return buyer;
    }



}
