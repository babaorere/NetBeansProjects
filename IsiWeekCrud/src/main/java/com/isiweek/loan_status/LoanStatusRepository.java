package com.isiweek.loan_status;

import org.springframework.data.jpa.repository.JpaRepository;


public interface LoanStatusRepository extends JpaRepository<LoanStatus, Long> {

    boolean existsByNameIgnoreCase(String name);

}
