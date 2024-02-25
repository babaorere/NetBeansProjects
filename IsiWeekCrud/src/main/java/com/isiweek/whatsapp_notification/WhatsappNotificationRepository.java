package com.isiweek.whatsapp_notification;

import com.isiweek.loan_contract.LoanContract;
import com.isiweek.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WhatsappNotificationRepository extends JpaRepository<WhatsappNotification, Long> {

    WhatsappNotification findFirstByLoanContract(LoanContract loanContract);

    WhatsappNotification findFirstByPerson(Person person);

}
