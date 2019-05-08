package com.thesisapp.thesisapp.DTO;

public class CityCountryPair {

    String cityName;
    String countryCode;

    public CityCountryPair(String cityName, String countryCode) {
        this.cityName = cityName;
        this.countryCode = countryCode;
    }

    public String getCityName() {
        return cityName;
    }
    public String getCountryCode() {
        return countryCode;
    }


}
