package com.thesisapp.thesisapp.Repository;

import com.thesisapp.thesisapp.Entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insertCity(String cityName, Integer ref_countryID) {
        String sql = "INSERT IGNORE INTO city (cityName, ref_countryID) VALUES ('"+cityName+"','"+ref_countryID+"')";
        this.jdbcTemplate.update(sql);
    }

    public String getCityIdByCityName(String cityName){
        return jdbcTemplate.queryForObject("SELECT cityID FROM city WHERE cityName=?", new Object[] {cityName},String.class);
    }

    public List<City> getCityByCityName(String cityName){
        return jdbcTemplate.query("SELECT * FROM city WHERE cityName='" + cityName + "'",
                new BeanPropertyRowMapper<City>(City.class));
    }
}
