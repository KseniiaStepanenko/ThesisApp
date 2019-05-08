package com.thesisapp.thesisapp.Controller;

import com.thesisapp.thesisapp.Entity.Country;
import com.thesisapp.thesisapp.Repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountryRepository countryRepository;

    @GetMapping("/getCountryDataByCode/{countryCode}")
    public List<Country> getCountryDataByCode(@PathVariable("countryCode") String countryCode) {
        List<Country> countryByCountryCode = countryRepository.getCountryDataByCountryCode(countryCode);
        return countryByCountryCode;
    }


}
