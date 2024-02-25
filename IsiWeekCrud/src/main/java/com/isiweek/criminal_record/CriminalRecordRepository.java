package com.isiweek.criminal_record;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CriminalRecordRepository extends JpaRepository<CriminalRecord, Long> {

    boolean existsByNameIgnoreCase(String name);

}
