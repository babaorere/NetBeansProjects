package com.isiweek.email_notification;

import com.isiweek.email_notification.EmailNotification;
import com.isiweek.loan_contract.domain.LoanContract;
import com.isiweek.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {

    EmailNotification findFirstByPerson(Person person);

    EmailNotification findFirstByLoanContract(LoanContract loanContract);

}
