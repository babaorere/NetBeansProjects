package com.isiweek.country;

import com.isiweek.country.Country;
import com.isiweek.country.Country;
import com.isiweek.country.CountryRepository;
import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
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

    public List<Country> findAll() {
        final List<Country> countries = countryRepository.findAll(Sort.by("id"));
        return countries.stream()
                .map(country -> mapToDTO(country, new Country()))
                .toList();
    }

    public Country get(final Long id) {
        return countryRepository.findById(id)
                .map(country -> mapToDTO(country, new Country()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final Country Country) {
        final Country country = new Country();
        mapToEntity(Country, country);
        return countryRepository.save(country).getId();
    }

    public void update(final Long id, final Country Country) {
        final Country country = countryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(Country, country);
        countryRepository.save(country);
    }

    public void delete(final Long id) {
        countryRepository.deleteById(id);
    }

    private Country mapToDTO(final Country country, final Country Country) {
        Country.setId(country.getId());
        Country.setName(country.getName());
        Country.setDescription(country.getDescription());
        return Country;
    }

    private Country mapToEntity(final Country Country, final Country country) {
        country.setName(Country.getName());
        country.setDescription(Country.getDescription());
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
