package com.thesisapp.thesisapp.DTO;

public class ShortestPathDTO {
    String ticketCombinationID;
    Double price;
    String airportFromList;
    String airportToList;

    public ShortestPathDTO() {
    }

    public String getTicketCombinationID() {
        return ticketCombinationID;
    }

    public void setTicketCombinationID(String ticketCombinationID) {
        this.ticketCombinationID = ticketCombinationID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAirportFromList() {
        return airportFromList;
    }

    public void setAirportFromList(String airportFromList) {
        this.airportFromList = airportFromList;
    }

    public String getAirportToList() {
        return airportToList;
    }

    public void setAirportToList(String airportToList) {
        this.airportToList = airportToList;
    }
}
