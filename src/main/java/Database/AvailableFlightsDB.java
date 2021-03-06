package Database;

import Model.Flight;

import java.sql.*;
import java.util.ArrayList;

public class AvailableFlightsDB extends genericDB {

    public AvailableFlightsDB(String databaseName) {
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
        String createStatement = "CREATE TABLE IF NOT EXISTS AvailableFlights (\n"
                + "	FlightId integer PRIMARY KEY,\n"
                + "	Origin text NOT NULL,\n"
                + "	Destination text NOT NULL,\n"
                + "	Price integer NOT NULL,\n"
                + "	DestinationAirport text NOT NULL,\n"
                + "	DateOfDeparture text NOT NULL,\n"
                + "	DateOfArrival text NOT NULL,\n"
                + " AirlineCompany text NOT NULL,\n"
                + " NumberOfTickets integer NOT NULL,\n"
                + " Baggage text NOT NULL,\n"
                + " TicketsType text NOT NULL,\n"
                + " VacationStyle text NOT NULL,\n"
                + " SellerUserName text NOT NULL,\n"
                + " OriginalPrice integer NOT NULL\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertVacation(Flight Data, int FlightId) throws SQLException {
        String insertStatement = "INSERT INTO AvailableFlights (FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName,OriginalPrice) VAlUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String url = "jdbc:sqlite:" + DBName + ".db";
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
            pstmt.setInt(14, Data.getOriginalPrice());
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
    public ArrayList<Flight> readVacation(Flight Data) {
        ArrayList<Flight> flights = new ArrayList<>();
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
        String sql = "SELECT Origin,Destionation,Price,DestinationAirport,DateOfDeparture,DateOfArrival,AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,SellerUserName FROM AvailableFlight WHERE " +
                "Origin='" + origin + "' AND Destionation= '" + destination + "' AND Price='" + price + "' AND DestinationAirport='" + destinationAirport + "'AND DateOfDeparture='" + dateOfDeparture + "'AND DateOfarrival='" + dateOfArrival + "'AND AirlineCompany='" + airlineCompany + "'AND NumberOfTickets='" + numOfTickets + "'AND Baggage='" + baggage + "' AND TicketsType='" + ticketsType + "'AND VacationStyle='" + vacationStyle + "'AND SellerUserName='" + seller + "' AND OriginalPrice= '" +originalPrice + "'";
        String url = "jdbc:sqlite:" + DBName + ".db";
        flights = getVacationsBasedOnQuery(url, sql);
        return flights;
    }



    //implement readUsers method which will display each available vacation which match given data
    public ArrayList<Flight> readMatchVacations(Flight Data) {
        ArrayList<Flight> flights = new ArrayList<>();
        String origin = Data.getOrigin();
        String destination = Data.getDestination();
        String dateOfDeparture = Data.getDateOfDeparture();
        String dateOfArrival = Data.getDateOfArrival();
        int numOfTickets = Data.getNumOfTickets();
        String url = "jdbc:sqlite:" + DBName + ".db";
        String query = "SELECT FlightId,Origin,Destination,Price,DestinationAirport,DateOfDeparture,DateOfArrival," +
                "AirlineCompany,NumberOfTickets,Baggage,TicketsType,VacationStyle,sellerUserName," +
                "OriginalPrice FROM AvailableFlights where Origin=? and Destination=? and DateOfDeparture=? " +
                "and DateOfArrival=? and NumberOfTickets>=?";
        //ArrayList<Flight> flights = new ArrayList<Flight>();
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,origin);
            stmt.setString(2,destination);
            stmt.setString(3,dateOfDeparture);
            stmt.setString(4,dateOfArrival);
            stmt.setInt(5,numOfTickets);
//            stmt.setString(6,Controller.currentUserName);
            ResultSet rs = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                //creating flights objects only so we could display them, there are not going to be a real availableVacation obects
                Flight flight = new Flight(rs.getInt("FlightId"),
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
                flights.add(flight);
                flight = null;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        //flights = getVacationsBasedOnQuery(url, sql);
        return flights;
        //flights = getVacationsBasedOnQuery(url, sql);
        //return flights;
    }


//    private ArrayList<Flight> getVacationsBasedOnQuery(String url, String query){
//       ArrayList<Flight> vacations = new ArrayList<Flight>();
//        try (Connection conn = DriverManager.getConnection(url);
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//
//            // loop through the result set
//            while (rs.next()) {
//                //creating vacation objects only so we could display them, there are not going to be a real availableVacation obects
//                Flight vacation = new Flight(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14));
//                vacations.add(vacation);
//                vacation = null;
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return vacations;
//    }


    public void deleteFromTable ( int FlightId){
        String deleteStatement = "DELETE FROM AvailableFlights WHERE FlightId = ?";
        String url = "jdbc:sqlite:" + DBName + ".db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(deleteStatement)) {
            // set the corresponding param
            pstmt.setInt(1, FlightId);
            // execute the deleteUser statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public int sdf(){
        String url = "jdbc:sqlite:" + DBName + ".db";
        String query = "SELECT MAX(FlightId) FROM AvailableFlights";
        try (Connection conn = DriverManager.getConnection(url);
             Statement pstmt = conn.createStatement()) {
                pstmt.execute(query);
            ResultSet rs = pstmt.getResultSet();
            while (rs.next()) {
                int w = rs.getInt(1);
                System.out.println(w);
                return w;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}