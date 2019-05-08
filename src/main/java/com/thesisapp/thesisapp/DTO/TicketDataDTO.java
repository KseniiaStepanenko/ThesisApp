package com.thesisapp.thesisapp.DTO;

public class TicketDataDTO {
    String cityNameFrom;
    String cityNameTo;
    String countryNameFrom;
    String countryNameTo;
    String airportFrom;
    String airportTo;
    String flightNumber;
    String price;
    String localDepartureDateTime;
    String localArrivalDateTime;
    String redirectLink;

    public TicketDataDTO(){}

    public TicketDataDTO(String cityNameFrom, String cityNameTo, String countryNameFrom, String countryNameTo, String airportFrom, String airportTo, String flightNumber, String price, String localDepartureDateTime, String localArrivalDateTime, String redirectLink) {
        this.cityNameFrom = cityNameFrom;
        this.cityNameTo = cityNameTo;
        this.countryNameFrom = countryNameFrom;
        this.countryNameTo = countryNameTo;
        this.airportFrom = airportFrom;
        this.airportTo = airportTo;
        this.flightNumber = flightNumber;
        this.price = price;
        this.localDepartureDateTime = localDepartureDateTime;
        this.localArrivalDateTime = localArrivalDateTime;
        this.redirectLink = redirectLink;
    }

    public String getCityNameFrom() {
        return cityNameFrom;
    }

    public void setCityNameFrom(String cityNameFrom) {
        this.cityNameFrom = cityNameFrom;
    }

    public String getCityNameTo() {
        return cityNameTo;
    }

    public void setCityNameTo(String cityNameTo) {
        this.cityNameTo = cityNameTo;
    }

    public String getCountryNameFrom() {
        return countryNameFrom;
    }

    public void setCountryNameFrom(String countryNameFrom) {
        this.countryNameFrom = countryNameFrom;
    }

    public String getCountryNameTo() {
        return countryNameTo;
    }

    public void setCountryNameTo(String countryNameTo) {
        this.countryNameTo = countryNameTo;
    }

    public String getAirportFrom() {
        return airportFrom;
    }

    public void setAirportFrom(String airportFrom) {
        this.airportFrom = airportFrom;
    }

    public String getAirportTo() {
        return airportTo;
    }

    public void setAirportTo(String airportTo) {
        this.airportTo = airportTo;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocalDepartureDateTime() {
        return localDepartureDateTime;
    }

    public void setLocalDepartureDateTime(String localDepartureDateTime) {
        this.localDepartureDateTime = localDepartureDateTime;
    }

    public String getLocalArrivalDateTime() {
        return localArrivalDateTime;
    }

    public void setLocalArrivalDateTime(String localArrivalDateTime) {
        this.localArrivalDateTime = localArrivalDateTime;
    }

    public String getRedirectLink() {
        return redirectLink;
    }

    public void setRedirectLink(String redirectLink) {
        this.redirectLink = redirectLink;
    }
}
