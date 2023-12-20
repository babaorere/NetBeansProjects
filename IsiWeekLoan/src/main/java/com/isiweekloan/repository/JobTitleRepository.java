package com.isiweekloan.repository;

import com.isiweekloan.entity.EmployeeEntity;
import com.isiweekloan.entity.JobTitleEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitleEntity, Long> {

    // Optional method to find by name (example)
    Optional<JobTitleEntity> findByName(String name);

    // You can add additional custom query methods here based on your needs
    // Custom query example using JPQL:
    @Query("SELECT e FROM EmployeeEntity e WHERE e.jobtitle.id = :jobTitleId")
    List<EmployeeEntity> findEmployeesByJobTitleId(Long jobTitleId);

}
