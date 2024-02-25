package com.isiweek.employee_status;

import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, Long> {

    boolean existsByNameIgnoreCase(String name);

}
