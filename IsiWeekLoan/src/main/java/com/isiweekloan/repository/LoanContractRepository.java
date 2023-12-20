package com.isiweekloan.repository;

import com.isiweekloan.entity.LoanContractEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanContractRepository extends JpaRepository<LoanContractEntity, Long> {

    // Optional method to find by customer ID (example)
    Optional<LoanContractEntity> findByCustomerId(Long customerId);

    // Find all active loan contracts (assuming "ACTIVE" is a value
    // in the LoanStatusEntity)
    @Query("SELECT lc FROM LoanContractEntity lc WHERE lc.loanStatus.name = 'ACTIVE'")
    List<LoanContractEntity> findAllActive();

    // You can add additional custom query methods here based on your needs
    // Custom query example using JPQL:
    @Query("SELECT lc FROM LoanContractEntity lc WHERE lc.date = DATE(:date)")
    List<LoanContractEntity> findByDate(java.util.Date date);

}
