package com.isiweek.loan_type.domain;

import com.isiweek.loan_type.domain.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByDescriptionIgnoreCase(String description);

}
