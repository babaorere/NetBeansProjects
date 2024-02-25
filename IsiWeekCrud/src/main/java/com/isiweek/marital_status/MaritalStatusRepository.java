package com.isiweek.marital_status;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MaritalStatusRepository extends JpaRepository<MaritalStatus, Long> {

    boolean existsByNameIgnoreCase(String name);

}
