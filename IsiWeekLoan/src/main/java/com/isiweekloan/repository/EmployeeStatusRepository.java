package com.isiweekloan.repository;

import com.isiweekloan.entity.EmployeeEntity;
import com.isiweekloan.entity.EmployeeStatusEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatusEntity, Long> {

    // Optional method to find by name (example)
    Optional<EmployeeStatusEntity> findByName(String name);

    // You can add additional custom query methods here based on your needs
    // Custom query example using JPQL:
    @Query("SELECT e FROM EmployeeEntity e WHERE e.employeeStatus.id = :statusId")
    List<EmployeeEntity> findEmployeesByStatusId(Long statusId);

}
