package com.thesisapp.thesisapp.Repository;

import com.thesisapp.thesisapp.Entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public String getCountryNameByID(String id) {
        return jdbcTemplate.queryForObject("SELECT countryName FROM country WHERE countryID=?", new Object[] {id},String.class);
    }

    public String getCountryIdByCountryCode(String countryCode){
        return jdbcTemplate.queryForObject("SELECT countryID FROM country WHERE countryCode=?", new Object[] {countryCode},String.class);
    }

    public List<Country> getCountryDataByCountryCode(String countryCode){
        String sql = "SELECT * FROM country WHERE countryCode='" + countryCode + "'";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<Country>(Country.class));
    }
}
