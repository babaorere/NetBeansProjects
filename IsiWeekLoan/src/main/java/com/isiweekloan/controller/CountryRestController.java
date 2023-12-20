package com.isiweekloan.controller;

import com.isiweekloan.entity.CountryEntity;
import com.isiweekloan.security.service.CountryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/countries")
public class CountryRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(CountryRestController.class);

    @Autowired
    private CountryService countryService;

    @PostMapping
    public ResponseEntity<CountryEntity> createCountry(@Valid @RequestBody CountryEntity country) {
        try {
            return new ResponseEntity<>(countryService.saveCountry(country), HttpStatus.CREATED);
        } catch (ValidationException e) {
            LOGGER.error("Validation error while creating country: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            LOGGER.error("Unexpected error while creating country: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryEntity> getCountryById(@PathVariable Long id) {
        try {
            CountryEntity country = countryService.findById(id);
            if (country == null) {
                return ResponseEntity.notFound().build();
            }
            return new ResponseEntity<>(country, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Unexpected error while fetching country by ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CountryEntity>> getAllCountries() {
        try {
            List<CountryEntity> countries = countryService.findAllCountries();
            if (countries.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(countries);
        } catch (Exception e) {
            LOGGER.error("Unexpected error while fetching all countries: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryEntity> updateCountry(@PathVariable Long id, @Valid @RequestBody CountryEntity country) {
        try {
            country.setId(id);
            return new ResponseEntity<>(countryService.updateCountry(country), HttpStatus.OK);
        } catch (ValidationException e) {
            LOGGER.error("Validation error while updating country: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            LOGGER.error("Country not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOGGER.error("Unexpected error while updating country: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        try {
            countryService.deleteCountry(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            LOGGER.error("Country not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            LOGGER.error("Unexpected error while deleting country: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}