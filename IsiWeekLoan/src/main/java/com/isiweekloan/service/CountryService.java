package com.isiweekloan.service;

import com.isiweekloan.entity.CountryEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<CountryEntity> getAllCountries() {
        return countryRepository.findAll();
    }

    public CountryEntity getCountryById(Long id) throws ResourceNotFoundException {
        return countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with ID: " + id));
    }

    public CountryEntity createCountry(CountryEntity country) {
        Objects.requireNonNull(country.getName(), "Country name cannot be null");
        Objects.requireNonNull(country.getDescription(), "Country description cannot be null");

        return countryRepository.save(country);
    }

    public CountryEntity updateCountry(Long id, CountryEntity country) throws ResourceNotFoundException {
        Optional<CountryEntity> optionalExistingCountry = countryRepository.findById(id);

        if (optionalExistingCountry.isPresent()) {
            CountryEntity existingCountry = optionalExistingCountry.get();
            existingCountry.setName(country.getName());
            existingCountry.setDescription(country.getDescription());

            return countryRepository.save(existingCountry);
        } else {
            throw new ResourceNotFoundException("Country not found with ID: " + id);
        }
    }

    public void deleteCountry(Long id) throws ResourceNotFoundException {
        if (countryRepository.existsById(id)) {
            countryRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Country not found with ID: " + id);
        }
    }
}

