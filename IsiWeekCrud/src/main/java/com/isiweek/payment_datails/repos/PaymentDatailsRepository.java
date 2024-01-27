package com.isiweek.payment_datails.repos;

import com.isiweek.loan_contract.domain.LoanContract;
import com.isiweek.payment_datails.domain.PaymentDatails;
import com.isiweek.payment_status.domain.PaymentStatus;
import com.isiweek.payment_type.domain.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDatailsRepository extends JpaRepository<PaymentDatails, Long> {

    PaymentDatails findFirstByLoanContract(LoanContract loanContract);

    PaymentDatails findFirstByPaymentType(PaymentType paymentType);

    PaymentDatails findFirstByPaymentStatus(PaymentStatus paymentStatus);

}
