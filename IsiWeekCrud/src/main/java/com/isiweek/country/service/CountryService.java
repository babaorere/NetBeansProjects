package com.isiweek.country.service;

import com.isiweek.country.domain.Country;
import com.isiweek.country.model.CountryDTO;
import com.isiweek.country.repos.CountryRepository;
import com.isiweek.person.domain.Person;
import com.isiweek.person.repos.PersonRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final PersonRepository personRepository;

    public CountryService(final CountryRepository countryRepository,
            final PersonRepository personRepository) {
        this.countryRepository = countryRepository;
        this.personRepository = personRepository;
    }

    public List<CountryDTO> findAll() {
        final List<Country> countries = countryRepository.findAll(Sort.by("id"));
        return countries.stream()
                .map(country -> mapToDTO(country, new CountryDTO()))
                .toList();
    }

    public CountryDTO get(final Long id) {
        return countryRepository.findById(id)
                .map(country -> mapToDTO(country, new CountryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CountryDTO countryDTO) {
        final Country country = new Country();
        mapToEntity(countryDTO, country);
        return countryRepository.save(country).getId();
    }

    public void update(final Long id, final CountryDTO countryDTO) {
        final Country country = countryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(countryDTO, country);
        countryRepository.save(country);
    }

    public void delete(final Long id) {
        countryRepository.deleteById(id);
    }

    private CountryDTO mapToDTO(final Country country, final CountryDTO countryDTO) {
        countryDTO.setId(country.getId());
        countryDTO.setName(country.getName());
        countryDTO.setDescription(country.getDescription());
        return countryDTO;
    }

    private Country mapToEntity(final CountryDTO countryDTO, final Country country) {
        country.setName(countryDTO.getName());
        country.setDescription(countryDTO.getDescription());
        return country;
    }

    public boolean nameExists(final String name) {
        return countryRepository.existsByNameIgnoreCase(name);
    }

    public String getReferencedWarning(final Long id) {
        final Country country = countryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Person countryPerson = personRepository.findFirstByCountry(country);
        if (countryPerson != null) {
            return WebUtils.getMessage("country.person.country.referenced", countryPerson.getId());
        }

        return null;
    }

}
