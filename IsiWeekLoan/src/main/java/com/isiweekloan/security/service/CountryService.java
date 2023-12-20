package com.isiweekloan.security.service;

import com.isiweekloan.entity.CountryEntity;
import com.isiweekloan.entity.PersonEntity;
import com.isiweekloan.exception.EntityInUseException;
import com.isiweekloan.repository.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CountryService {

    private final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private Validator validator;

    @Autowired
    private CountryRepository countryRepository;

    @Transactional
    public CountryEntity saveCountry(@Valid CountryEntity country) {
        validateEntity(country); // Separate method for cleaner code

        try {
            return countryRepository.save(country);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Country with same name already exists.", e);
        }
    }

    @Transactional
    public CountryEntity updateCountry(@Valid CountryEntity country) {
        validateEntity(country); // Reuse validation method

        Long id = country.getId();
        CountryEntity existingCountry = findById(id);

        // Update specific fields instead of replacing the entire entity
        existingCountry.setName(country.getName());
        existingCountry.setDescription(country.getDescription());

        return countryRepository.save(existingCountry);
    }

    @Transactional
    public void deleteCountry(Long id) throws EntityInUseException {
        Optional<CountryEntity> country = countryRepository.findById(id);
        if (country.isPresent()) {
            // Check if the country has any associated persons
            List<PersonEntity> linkedPersons = (List<PersonEntity>) country.get().getPersonEntityCollection();
            if (!linkedPersons.isEmpty()) {
                throw new EntityInUseException("Country cannot be deleted because it has associated persons.");
            }

            countryRepository.deleteById(id);
            LOGGER.info("Deleted country with ID {}", id);
        } else {
            throw new EntityNotFoundException("Country not found with ID " + id);
        }
    }

    public CountryEntity findById(Long id) {
        Optional<CountryEntity> country = countryRepository.findById(id);
        return country.orElseThrow(() -> new EntityNotFoundException("Country not found with ID " + id));
    }

    public List<CountryEntity> findAllCountries() {
        return countryRepository.findAll();
    }

    private void validateEntity(CountryEntity country) {
        Set<ConstraintViolation<CountryEntity>> violations = validator.validate(country);
        if (!violations.isEmpty()) {
            throw new ValidationException((Throwable) violations);
        }
    }
}
