package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
