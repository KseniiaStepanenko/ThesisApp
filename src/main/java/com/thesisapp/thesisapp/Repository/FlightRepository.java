package com.thesisapp.thesisapp.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FlightRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insertFlight(String flightNumber, String airlineCode, Integer airportFrom, Integer airportTo) {
        String sql = "INSERT IGNORE INTO flight (flightNumber, airlineCode, airportFrom, airportTo) VALUES ('"+flightNumber+"','"+airlineCode+"',"+airportFrom+","+airportTo+")";
        this.jdbcTemplate.update(sql);
    }

    public String getFlightIdByFlightNumber(String flightNumber){
        return jdbcTemplate.queryForObject("SELECT flightID FROM flight WHERE flightNumber=?", new Object[] {flightNumber},String.class);
    }

}
