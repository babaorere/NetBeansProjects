package com.isiweek.whatsapp_notification.repos;

import com.isiweek.loan_contract.domain.LoanContract;
import com.isiweek.person.domain.Person;
import com.isiweek.whatsapp_notification.domain.WhatsappNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhatsappNotificationRepository extends JpaRepository<WhatsappNotification, Long> {

    WhatsappNotification findFirstByPerson(Person person);

    WhatsappNotification findFirstByLoanContract(LoanContract loanContract);

}
