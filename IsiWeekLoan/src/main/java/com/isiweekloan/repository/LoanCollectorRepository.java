package com.isiweekloan.repository;

import com.isiweekloan.entity.LoanCollectorEntity;
import com.isiweekloan.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanCollectorRepository extends JpaRepository<LoanCollectorEntity, Long>, JpaSpecificationExecutor<LoanCollectorEntity> {
}