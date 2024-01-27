package com.isiweek.country;

import com.isiweek.country.Country;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/countries", produces = MediaType.APPLICATION_JSON_VALUE)
public class CountryResource {

    private final CountryService countryService;

    public CountryResource(final CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(countryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountry(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(countryService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createCountry(@RequestBody @Valid final Country Country) {
        final Long createdId = countryService.create(Country);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCountry(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final Country Country) {
        countryService.update(id, Country);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable(name = "id") final Long id) {
        countryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
