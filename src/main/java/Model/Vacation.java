package Model;

import java.sql.Date;

public class Vacation {
    //here or at model  or both(?)
   // private static int vacationId = 0;
    private int vacationId;
    private String origin;
    private String destination;
    private int price;
    private String destinationAirport;

    //missing in the table need to be fixed
    //private String dateOfDeparture;
    private String dateOfArrival;
    private String airlineCompany;
    private int numOfTickets;
    //None/Only hand bag/Up to 8 kg/Up to 23 kg/ Up to 31 kg/ More than 31 kg
    private String baggage;
    //adult,child,baby
    private String ticketsType;
    //Urban/ Exotic/ Natures/ Multi
    private String vacationStyle;
    private String seller;

    public Vacation(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, int vacationId, String seller) {
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.destinationAirport = destinationAirport;
        //this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.airlineCompany = airlineCompany;
        this.numOfTickets = numOfTickets;
        this.baggage = baggage;
        this.ticketsType = ticketsType;
        this.vacationStyle = vacationStyle;
        this.seller=seller;
    }

    public int getVacationId() {
        return vacationId;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getPrice() {
        return price;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

//    public String getDateOfDeparture() {
//        return dateOfDeparture;
//    }

    public String getDateOfArrival() {
        return dateOfArrival;
    }

    public String getAirlineCompany() {
        return airlineCompany;
    }

    public int getNumOfTickets() {
        return numOfTickets;
    }

    public String getBaggage() {
        return baggage;
    }

    public String getTicketsType() {
        return ticketsType;
    }

    public String getVacationStyle() {
        return vacationStyle;
    }

    public String getSeller() {
        return seller;
    }
}
