package Database;

import Model.Vacation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AvailableVacationsDB extends genericDB {

    private String databaseName;

    public AvailableVacationsDB(String databaseName) {
        super(databaseName);
    }

    public void insertVacation(String tableName, Vacation Data, int vacationId) throws SQLException {
        String insertStatement = "INSERT INTO AvailableVacations (VacationId,Origin,Destination,Price,DestinationAirport,DateOfarrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName) VAlUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        String url = "jdbc:sqlite:" + databaseName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            pstmt.setInt(1,Data.getVacationId());
            pstmt.setString(2,Data.getOrigin());
            pstmt.setString(3,Data.getDestination());
            pstmt.setInt(4,Data.getPrice());
            pstmt.setString(5,Data.getDestinationAirport());
            pstmt.setString(6,Data.getDateOfArrival());
            pstmt.setString(7,Data.getAirlineCompany());
            pstmt.setInt(8,Data.getNumOfTickets());
            pstmt.setString(9,Data.getBaggage());
            pstmt.setString(10,Data.getTicketsType());
            pstmt.setString(11,Data.getVacationStyle());
            pstmt.setString(12,Data.getSeller());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw(new SQLException(e));
        }
    }

    //implement read method which will display eace available vacation

    //implement update method (by user)

    //



}
