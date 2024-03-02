package com.isiweek.debtor;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DebtorRepository extends JpaRepository<Debtor, Long> {

    boolean existsByNameIgnoreCase(String name);

}
