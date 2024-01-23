package com.isiweek.loan_status.repos;

import com.isiweek.loan_status.domain.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanStatusRepository extends JpaRepository<LoanStatus, Long> {

    boolean existsByNameIgnoreCase(String name);

}
