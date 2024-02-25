package com.isiweek.country;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CountryRepository extends JpaRepository<Country, Long> {

    boolean existsByNameIgnoreCase(String name);

}
