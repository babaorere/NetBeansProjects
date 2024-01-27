package com.isiweek.loan_collector_status.repos;

import com.isiweek.loan_collector_status.domain.LoanCollectorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanCollectorStatusRepository extends JpaRepository<LoanCollectorStatus, Long> {

    boolean existsByNameIgnoreCase(String name);

}
