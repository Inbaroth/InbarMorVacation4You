package Database;

import Model.Vacation;

import java.sql.*;
import java.util.ArrayList;

public class AvailableVacationsDB extends genericDB {

    private String databaseName;

    public AvailableVacationsDB(String databaseName) {
        super(databaseName);
    }

    public void insertVacation(Vacation Data, int vacationId) throws SQLException {
        String insertStatement = "INSERT INTO AvailableVacations (VacationId,Origin,Destionation,Price,DestinationAirport,DateOfDeparture,DateOfarrival,AirlineCompny,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName,OriginalPrice) VAlUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String url = "jdbc:sqlite:" + databaseName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
            // set the corresponding parameters
            pstmt.setInt(1, Data.getVacationId());
            pstmt.setString(2, Data.getOrigin());
            pstmt.setString(3, Data.getDestination());
            pstmt.setInt(4, Data.getPrice());
            pstmt.setString(5, Data.getDestinationAirport());
            pstmt.setString(6, Data.getDateOfDeparture());
            pstmt.setString(7, Data.getDateOfArrival());
            pstmt.setString(8, Data.getAirlineCompany());
            pstmt.setInt(9, Data.getNumOfTickets());
            pstmt.setString(10, Data.getBaggage());
            pstmt.setString(11, Data.getTicketsType());
            pstmt.setString(12, Data.getVacationStyle());
            pstmt.setString(13, Data.getSeller());
            pstmt.setInt(13, Data.getOriginalPrice());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw (new SQLException(e));
        }
    }

    //ATTENTION:
    //this method search by ALL columns data except for VacationId,
    //maybe there will be another usage for this
    //maybe return by using it all vacations by removing the "WHERE" parts
//    //implement readUsers method which will display eace available vacation
    public ArrayList<Vacation> readVacation(Vacation Data) {
        ArrayList<Vacation> vacations = new ArrayList<>();
        String origin = Data.getOrigin();
        String destination = Data.getDestination();
        int price = Data.getPrice();
        String destinationAirport = Data.getDestinationAirport();
        String dateOfDeparture = Data.getDateOfDeparture();
        String dateOfArrival = Data.getDateOfArrival();
        String airlineCompany = Data.getAirlineCompany();
        int numOfTickets = Data.getNumOfTickets();
        //None/Only hand bag/Up to 8 kg/Up to 23 kg/ Up to 31 kg/ More than 31 kg
        String baggage = Data.getBaggage();
        //adult,child,baby
        String ticketsType = Data.getTicketsType();
        //Urban/ Exotic/ Natures/ Multi
        String vacationStyle = Data.getVacationStyle();
        String seller = Data.getSeller();
        int originalPrice = Data.getOriginalPrice();
        String sql = "SELECT Origin,Destionation,Price,DestinationAirport,DateOfDeparture,DateOfarrival,AirlineCompny,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName FROM AvailableVacationas WHERE " +
                "Origin='" + origin + "' AND Destionation= '" + destination + "' AND Price='" + price + "' AND DestinationAirport='" + destinationAirport + "'AND DateOfDeparture='" + dateOfDeparture + "'AND DateOfarrival='" + dateOfArrival + "'AND AirlineCompny='" + airlineCompany + "'AND NumberOfTickets='" + numOfTickets + "'AND Baggage='" + baggage + "' AND TicketsType='" + ticketsType + "'AND VacationStyle='" + vacationStyle + "'AND SellerUserName='" + seller + "' AND OriginalPrice= '" +originalPrice + "'";
        String url = "jdbc:sqlite:" + databaseName + ".db";
        vacations = getVacationsBasedOnQuery(url, sql);
        return vacations;
    }



    //implement readUsers method which will display each available vacation which match given data
    public ArrayList<Vacation> readMatchVacations( Vacation Data) {
        ArrayList<Vacation> vacations = new ArrayList<>();
        String origin = Data.getOrigin();
        String destination = Data.getDestination();
        String dateOfDeparture = Data.getDateOfDeparture();
        String dateOfArrival = Data.getDateOfArrival();
        int numOfTickets = Data.getNumOfTickets();
        String sql = "SELECT Origin,Destionation,Price,DestinationAirport,DateOfDeparture,DateOfarrival,AirlineCompny,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName,OriginalPrice FROM AvailableVacationas WHERE " +
                "Origin='" + origin + "' AND Destionation= '" + destination  + "'AND DateOfDeparture='" + dateOfDeparture + "'AND DateOfarrival='" + dateOfArrival  + "'AND NumberOfTickets>='" + numOfTickets + "' AND WHERE NOT EXIST (SELECT VacationId FROM PurchasedVacations  WHERE VacationId=AvailableVacationas.VacationId )";
        String url = "jdbc:sqlite:" + databaseName + ".db";
        vacations = getVacationsBasedOnQuery(url, sql);
        return vacations;
    }


    private ArrayList<Vacation> getVacationsBasedOnQuery(String url, String query){
       ArrayList<Vacation> vacations = new ArrayList<Vacation>();
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // loop through the result set
            while (rs.next()) {
                //creating vacation objects only so we could display them, there are not going to be a real availableVacation obects
                Vacation vacation = new Vacation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14));
                vacations.add(vacation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vacations;
    }

    //implement updateUser method (by user)

    //


}
