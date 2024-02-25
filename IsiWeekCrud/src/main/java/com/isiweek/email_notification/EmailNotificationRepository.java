package com.isiweek.email_notification;

import com.isiweek.loan_contract.LoanContract;
import com.isiweek.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {

    EmailNotification findFirstByLoanContract(LoanContract loanContract);

    EmailNotification findFirstByPerson(Person person);

}
