package com.isiweek.loan_collector_status;

import org.springframework.data.jpa.repository.JpaRepository;


public interface LoanCollectorStatusRepository extends JpaRepository<LoanCollectorStatus, Long> {

    boolean existsByNameIgnoreCase(String name);

}
