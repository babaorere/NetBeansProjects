package com.isiweek.phone_notification;

import com.isiweek.loan_contract.LoanContract;
import com.isiweek.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PhoneNotificationRepository extends JpaRepository<PhoneNotification, Long> {

    PhoneNotification findFirstByLoanContract(LoanContract loanContract);

    PhoneNotification findFirstByPerson(Person person);

}
