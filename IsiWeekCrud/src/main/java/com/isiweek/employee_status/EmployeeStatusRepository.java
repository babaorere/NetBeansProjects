package com.isiweek.employee_status;

import com.isiweek.employee_status.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, Long> {

    boolean existsByNameIgnoreCase(String name);

}
