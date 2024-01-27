package com.isiweek.phone_notification.repos;

import com.isiweek.loan_contract.domain.LoanContract;
import com.isiweek.person.Person;
import com.isiweek.phone_notification.domain.PhoneNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNotificationRepository extends JpaRepository<PhoneNotification, Long> {

    PhoneNotification findFirstByPerson(Person person);

    PhoneNotification findFirstByLoanContract(LoanContract loanContract);

}
