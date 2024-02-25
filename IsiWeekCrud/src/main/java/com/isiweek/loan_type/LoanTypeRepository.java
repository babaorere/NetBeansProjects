package com.isiweek.loan_type;

import org.springframework.data.jpa.repository.JpaRepository;


public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByDescriptionIgnoreCase(String description);

}
