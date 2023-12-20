package com.isiweekloan.repository;

import com.isiweekloan.entity.EmployeeEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    // Optional method to find by date of hire (example)
    @Query("SELECT e FROM EmployeeEntity e WHERE e.dateOfHire = DATE(:dateOfHire)")
    Optional<EmployeeEntity> findByDateOfHire(Date dateOfHire);

    // Optional method to find by department (example)
    Optional<EmployeeEntity> findByDepartment_Id(Long departmentId);

    // Optional method to find by job title (example)
    Optional<EmployeeEntity> findByJobtitle_Id(Long jobTitleId);

    // You can add additional custom query methods here based on your needs
    // Custom query example using JPQL:
    @Query("SELECT e FROM EmployeeEntity e WHERE e.salary > :minSalary AND e.performanceReviews LIKE :keyword")
    List<EmployeeEntity> findHighPerformersWithSalaryAbove(BigDecimal minSalary, String keyword);

}
