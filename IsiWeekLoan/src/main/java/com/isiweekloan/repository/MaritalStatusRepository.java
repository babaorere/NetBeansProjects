package com.isiweekloan.repository;

import com.isiweekloan.entity.MaritalStatusEntity;
import com.isiweekloan.entity.PersonEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MaritalStatusRepository extends JpaRepository<MaritalStatusEntity, Long> {

    // Optional method to find by name (example)
    Optional<MaritalStatusEntity> findByName(String name);

    // You can add additional custom query methods here based on your needs
    // Custom query example using JPQL:
    // Assuming "MARRIED" is the identifier for married status in PersonEntity
    @Query("SELECT p FROM PersonEntity p WHERE p.maritalStatus.name = 'MARRIED'")
    List<PersonEntity> findAllMarriedPeople();

}
