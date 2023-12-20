package com.isiweekloan.repository;

import com.isiweekloan.entity.CustomerEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    // Optional method to find by credit score (example)
    Optional<CustomerEntity> findByCreditScore(long creditScore);

    // Optional method to find by PersonEntity ID (example)
    Optional<CustomerEntity> findByPersonId(Long personId);

    // You can add additional custom query methods here based on your needs
    // Custom query example using JPQL:
    @Query("SELECT c FROM CustomerEntity c WHERE c.creditScore > :minScore AND c.maxLoanAmount > :minAmount")
    List<CustomerEntity> findEligibleCustomers(long minScore, BigDecimal minAmount);
}
