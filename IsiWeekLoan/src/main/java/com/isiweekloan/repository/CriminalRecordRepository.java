package com.isiweekloan.repository;

import com.isiweekloan.entity.CriminalRecordEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriminalRecordRepository extends JpaRepository<CriminalRecordEntity, Long> {

    // Optional method to find by name (example)
    Optional<CriminalRecordEntity> findByName(String name);

    // You can add additional custom query methods here based on your needs
}
