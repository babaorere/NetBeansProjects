package com.isiweek.loan_collector;

import com.isiweek.loan_collector_status.LoanCollectorStatus;
import com.isiweek.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoanCollectorRepository extends JpaRepository<LoanCollector, Long> {

    LoanCollector findFirstByPerson(Person person);

    LoanCollector findFirstByLcStatus(LoanCollectorStatus loanCollectorStatus);

    boolean existsByIdPerson(Long idPerson);

}
