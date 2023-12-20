package com.isiweekloan.repository;

import com.isiweekloan.entity.CountryEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    // Optional method to find country by its name
    Optional<CountryEntity> findByName(String name);

    // You can add additional custom query methods here based on your specific needs
}
