package com.thesisapp.thesisapp.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AirportRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insertAirport(String airportCode, Integer ref_cityID) {
        String sql = "INSERT IGNORE INTO airport (airpodeCode, ref_cityID) VALUES ('"+airportCode+"',"+ref_cityID+")";
        this.jdbcTemplate.update(sql);
    }

    public String getAirportIdByAirportCode(String airportCode){
        return jdbcTemplate.queryForObject("SELECT airportID FROM airport WHERE airpodeCode=?", new Object[] {airportCode},String.class);
    }
}
