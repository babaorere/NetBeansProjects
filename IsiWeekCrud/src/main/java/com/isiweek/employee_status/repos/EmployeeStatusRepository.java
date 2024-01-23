package com.isiweek.employee_status.repos;

import com.isiweek.employee_status.domain.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, Long> {

    boolean existsByNameIgnoreCase(String name);

}
