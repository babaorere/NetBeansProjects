package com.isiweek.loan_collector.repos;

import com.isiweek.loan_collector.domain.LoanCollector;
import com.isiweek.loan_collector_status.domain.LoanCollectorStatus;
import com.isiweek.person.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanCollectorRepository extends JpaRepository<LoanCollector, Long> {

    LoanCollector findFirstByPerson(Person person);

    LoanCollector findFirstByLcStatus(LoanCollectorStatus loanCollectorStatus);

    boolean existsByIdPerson(Long idPerson);

}
