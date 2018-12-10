package Database;

import Model.Vacation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PurchasedVacationsDB extends genericDB{
    private String databaseName;

    public PurchasedVacationsDB(String databaseName) { super(databaseName); }

    public void insertVacation( int vacationId,String date, String time,String  userName, int creditCard, String expirationDate, int csv) throws SQLException {
        String insertStatement = "INSERT INTO PurchcasedVacations (VacationId,DateOfPurchase,TimeOfPurchase,UserName,CreditCardNumber,ExpirationDate,CSV) VAlUES (?,?,?,?,?,?,?)";
        String url = "jdbc:sqlite:" + databaseName + ".db";
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
