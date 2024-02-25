package com.isiweek.company;

import com.isiweek.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findFirstByStatus(Status statusEntity);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByTaxidnumberIgnoreCase(String taxidnumber);

    boolean existsByNameIgnoreCase(String name);

}
