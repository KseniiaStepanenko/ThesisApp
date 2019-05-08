package com.thesisapp.thesisapp.DTO;

public class DirectionFromToDTO {
    String airportFromList;
    String airportToList;

    public DirectionFromToDTO() {}
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
