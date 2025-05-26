package com.caw24g.grupp_uppgift.controllers;

import com.caw24g.grupp_uppgift.models.City;
import com.caw24g.grupp_uppgift.repositories.CityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public ResponseEntity<List<City>> getAllCities() {
        try {
            List<City> cities = cityRepository.findAll();
            return ResponseEntity.ok(cities);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
