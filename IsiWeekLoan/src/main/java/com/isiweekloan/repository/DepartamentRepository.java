package com.isiweekloan.repository;

import com.isiweekloan.entity.DepartamentEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentRepository extends JpaRepository<DepartamentEntity, Long> {

    // Optional method to find by name (example)
    Optional<DepartamentEntity> findByName(String name);

    // You can add additional custom query methods here based on your needs
}
