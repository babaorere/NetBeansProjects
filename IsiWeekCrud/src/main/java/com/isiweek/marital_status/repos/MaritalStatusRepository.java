package com.isiweek.marital_status.repos;

import com.isiweek.marital_status.domain.MaritalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaritalStatusRepository extends JpaRepository<MaritalStatus, Long> {

    boolean existsByNameIgnoreCase(String name);

}
