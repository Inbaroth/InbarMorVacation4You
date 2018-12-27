package Model;

public class AvailableFlights extends Flights {
    public AvailableFlights(int vacationId, String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice) {
        super(vacationId, origin, destination, price, destinationAirport, dateOfDeparture, dateOfArrival, airlineCompany, numOfTickets, baggage, ticketsType, vacationStyle, seller, originalPrice);
    }

    public AvailableFlights(String origin, String destination, int price, String destinationAirport, String dateOfDeparture, String dateOfArrival, String airlineCompany, int numOfTickets, String baggage, String ticketsType, String vacationStyle, String seller, int originalPrice) {
        super(origin, destination, price, destinationAirport, dateOfDeparture, dateOfArrival, airlineCompany, numOfTickets, baggage, ticketsType, vacationStyle, seller, originalPrice);
    }

    public AvailableFlights(String origin, String destination, String dateOfDeparture, String dateOfArrival, int numOfTickets) {
        super(origin, destination, dateOfDeparture, dateOfArrival, numOfTickets);
    }
}
