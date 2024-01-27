package com.isiweek.country;

import com.isiweek.country.Country;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CountryRepository extends JpaRepository<Country, Long> {

    boolean existsByNameIgnoreCase(String name);

}
