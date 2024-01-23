package com.isiweek.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyStatusRepository extends JpaRepository<CompanyStatus, Long> {

    boolean existsByStatusNameIgnoreCase(String email);

}
