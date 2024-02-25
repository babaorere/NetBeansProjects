package com.isiweek.loan_contract;

import com.isiweek.lender.Lender;
import com.isiweek.loan_status.LoanStatus;
import com.isiweek.loan_type.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoanContractRepository extends JpaRepository<LoanContract, Long> {

    LoanContract findFirstByCustomer(Lender lender);

    LoanContract findFirstByLoanType(LoanType loanType);

    LoanContract findFirstByLoanStatus(LoanStatus loanStatus);

}
