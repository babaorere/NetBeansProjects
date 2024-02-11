package com.isiweek.company;

import com.isiweek.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyStatusRepository extends JpaRepository<Status, Long> {

}
