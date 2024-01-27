package com.isiweek.loan_contract.repos;

import com.isiweek.customer.Customer;
import com.isiweek.loan_contract.domain.LoanContract;
import com.isiweek.loan_status.domain.LoanStatus;
import com.isiweek.loan_type.domain.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanContractRepository extends JpaRepository<LoanContract, Long> {

    LoanContract findFirstByLoanType(LoanType loanType);

    LoanContract findFirstByLoanStatus(LoanStatus loanStatus);

    LoanContract findFirstByCustomer(Customer customer);

}
