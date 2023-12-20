package com.isiweekloan.repository;

import com.isiweekloan.entity.CustomerEntity;
import com.isiweekloan.entity.PersonEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    // Optional method to find by email (example)
    Optional<PersonEntity> findByEmail(String email);

    // Custom query example: find all customers with specific marital status
    @Query("SELECT c FROM CustomerEntity c WHERE c.person.maritalStatus.name = :maritalStatusName")
    List<CustomerEntity> findAllCustomersByMaritalStatus(String maritalStatusName);

    // You can add additional custom queries based on your needs
}
