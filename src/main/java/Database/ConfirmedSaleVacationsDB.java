package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConfirmedSaleVacationsDB extends genericDB {
    private String databaseName;

    public ConfirmedSaleVacationsDB(String databaseName) { super(databaseName); }


    public void insertVacation( int vacationId,String seller, String buyer) throws SQLException {
        String insertStatement = "INSERT INTO ConfirmedSaleVacations (VacationId,seller,buyer) VAlUES (?,?,?)";
        String url = "jdbc:sqlite:" + databaseName + ".db";
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
        String deleteStatement = "DELETE FROM ConfirmedSaleVacations WHERE vacationId = ?";
        String url = "jdbc:sqlite:" + databaseName + ".db";
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





}
