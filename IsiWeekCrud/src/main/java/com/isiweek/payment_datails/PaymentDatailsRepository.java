package com.isiweek.payment_datails;

import com.isiweek.loan_contract.LoanContract;
import com.isiweek.payment_status.PaymentStatus;
import com.isiweek.payment_type.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentDatailsRepository extends JpaRepository<PaymentDatails, Long> {

    PaymentDatails findFirstByPaymentType(PaymentType paymentType);

    PaymentDatails findFirstByLoanContract(LoanContract loanContract);

    PaymentDatails findFirstByPaymentStatus(PaymentStatus paymentStatus);

}
