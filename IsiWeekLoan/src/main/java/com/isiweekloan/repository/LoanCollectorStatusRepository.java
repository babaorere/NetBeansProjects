package com.isiweekloan.repository;

import com.isiweekloan.entity.LoanCollectorStatusEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanCollectorStatusRepository extends JpaRepository<LoanCollectorStatusEntity, Long> {

    // Optional method to find by name (example)
    Optional<LoanCollectorStatusEntity> findByName(String name);

    // You can add additional custom query methods here based on your needs
    // Custom query example using JPQL:
    @Query("SELECT lc FROM LoanCollectorStatusEntity lc WHERE lc.name = 'ACTIVE'")
    List<LoanCollectorStatusEntity> findAllActive();

}
