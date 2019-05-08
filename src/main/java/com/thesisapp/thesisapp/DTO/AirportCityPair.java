package com.thesisapp.thesisapp.DTO;

public class AirportCityPair {

    String airportCode;
    String cityName;

    public AirportCityPair(String airportCode, String cityName) {
        this.airportCode = airportCode;
        this.cityName = cityName;
    }

    public String getAirportCode() {
        return airportCode;
    }
    public String getCityName() {
        return cityName;
    }

}