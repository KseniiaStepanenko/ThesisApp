package com.thesisapp.thesisapp.Entity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ApiDataEntity {

    String flyFrom;
    String cityFrom;
    String countryFromCode;
    String countryFromName;
    String local_departure;
    String flyTo;
    String cityTo;
    String countryToCode;
    String countryToName;
    String local_arrival;
    String deep_link;
    String combination_id;
    String flightNo;
    String airline;
    Double price;

    public ApiDataEntity(JSONObject jsonObject){
        this.flyFrom = jsonObject.get("flyFrom").toString();
        this.cityFrom = jsonObject.get("cityFrom").toString();
        this.price = Double.parseDouble(jsonObject.get("price").toString());
        JSONObject jsonObject1 = (JSONObject) jsonObject.get("countryFrom");
        this.countryFromCode = jsonObject1.get("code").toString();
        this.countryFromName = jsonObject1.get("name").toString();
        this.local_departure = jsonObject.get("local_departure").toString();
        this.flyTo = jsonObject.get("flyTo").toString();
        this.cityTo = jsonObject.get("cityTo").toString();
        this.local_arrival = jsonObject.get("local_arrival").toString();
        this.deep_link = jsonObject.get("deep_link").toString();
        JSONObject jsonObject2 = (JSONObject) jsonObject.get("countryTo");
        this.countryToCode = jsonObject2.get("code").toString();
        this.countryToName = jsonObject2.get("name").toString();
        JSONArray jsonArray = (JSONArray) jsonObject.get("route");
        JSONObject jsonObject3 = (JSONObject) jsonArray.get(0);
        this.combination_id= jsonObject3.get("combination_id").toString();
        this.flightNo = jsonObject3.get("airline")+""+jsonObject3.get("flight_no").toString();
        this.airline = jsonObject3.get("airline").toString();
    }

    public String getFlyFrom() {
        return flyFrom;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public String getCountryFromCode() {
        return countryFromCode;
    }

    public String getCountryFromName() {
        return countryFromName;
    }

    public String getLocal_departure() {
        return local_departure;
    }

    public String getFlyTo() {
        return flyTo;
    }

    public String getCityTo() {
        return cityTo;
    }

    public String getCountryToCode() {
        return countryToCode;
    }

    public String getCountryToName() {
        return countryToName;
    }

    public String getLocal_arrival() {
        return local_arrival;
    }

    public String getDeep_link() {
        return deep_link;
    }

    public String getCombination_id() {
        return combination_id;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getAirline() {return airline;    }

    public void setAirline(String airline) {this.airline = airline;}

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
