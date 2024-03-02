package com.isiweek.company;

import com.isiweek.status.Status;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findFirstByStatus(Status statusEntity);

    Boolean existsByEmailIgnoreCase(String email);

    Boolean existsByTaxidnumberIgnoreCase(String taxidnumber);

    Boolean existsByNameIgnoreCase(String name);

}
