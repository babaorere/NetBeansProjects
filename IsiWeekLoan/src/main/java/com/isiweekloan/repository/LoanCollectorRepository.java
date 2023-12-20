package com.isiweekloan.repository;

import com.isiweekloan.entity.LoanCollectorEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanCollectorRepository extends JpaRepository<LoanCollectorEntity, Long> {

    // Optional method to find by person ID (example)
    Optional<LoanCollectorEntity> findByPersonId(Long personId);

    // Find all active loan collectors (assuming "ACTIVE" is a value
    // in the LoanCollectorStatusEntity)
    @Query("SELECT lc FROM LoanCollectorEntity lc WHERE lc.lcStatus.name = 'ACTIVE'")
    List<LoanCollectorEntity> findAllActive();

    // You can add additional custom query methods here based on your needs
    // Custom query example using JPQL:
    @Query("SELECT lc FROM LoanCollectorEntity lc WHERE lc.lcStatus.id = :statusId")
    List<LoanCollectorEntity> findByStatusId(Long statusId);

}
