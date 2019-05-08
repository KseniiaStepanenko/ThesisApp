package com.thesisapp.thesisapp.Controller;

import com.thesisapp.thesisapp.Entity.City;
import com.thesisapp.thesisapp.Repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
public class CityController {
    @Autowired
    CityRepository cityRepository;

    @GetMapping("/getCityDataByCityName/{cityName}")
    public List<City> getCityDataByCityName(@PathVariable("cityName") String cityName) {
        List<City> cityByName = cityRepository.getCityByCityName(cityName);
        return cityByName;
    }
}
