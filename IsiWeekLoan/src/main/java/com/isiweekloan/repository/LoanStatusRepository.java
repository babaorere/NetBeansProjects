package com.isiweekloan.repository;

import com.isiweekloan.entity.LoanContractEntity;
import com.isiweekloan.entity.LoanStatusEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanStatusRepository extends JpaRepository<LoanStatusEntity, Long> {

    // Optional method to find by name (example)
    Optional<LoanStatusEntity> findByName(String name);

    // You can add additional custom query methods here based on your needs
    // Custom query example using JPQL:
    @Query("SELECT lc FROM LoanContractEntity lc WHERE lc.loanStatus.name = :statusName")
    List<LoanContractEntity> findAllLoanContractsByStatusName(String statusName);

}
