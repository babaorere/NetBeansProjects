package com.isiweekloan.repository;

import com.isiweekloan.entity.DocTypeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocTypeRepository extends JpaRepository<DocTypeEntity, Long> {

    // Optional method to find by name (example)
    Optional<DocTypeEntity> findByName(String name);

    // You can add additional custom query methods here based on your needs
}