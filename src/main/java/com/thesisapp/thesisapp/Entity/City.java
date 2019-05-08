package com.thesisapp.thesisapp.Entity;

public class City {

    int cityId;
    String cityName;
    int ref_countryID;

    public City() {
    }

    public City(String cityName, int ref_countryID) {
        this.cityName = cityName;
        this.ref_countryID = ref_countryID;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getRef_countryID() {
        return ref_countryID;
    }

    public void setRef_countryID(int ref_countryID) {
        this.ref_countryID = ref_countryID;
    }
}
