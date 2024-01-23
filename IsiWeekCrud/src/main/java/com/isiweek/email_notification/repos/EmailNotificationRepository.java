package com.isiweek.email_notification.repos;

import com.isiweek.email_notification.domain.EmailNotification;
import com.isiweek.loan_contract.domain.LoanContract;
import com.isiweek.person.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {

    EmailNotification findFirstByPerson(Person person);

    EmailNotification findFirstByLoanContract(LoanContract loanContract);

}
