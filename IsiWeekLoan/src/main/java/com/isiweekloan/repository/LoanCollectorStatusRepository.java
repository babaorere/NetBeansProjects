package com.isiweekloan.repository;

import com.isiweekloan.entity.LoanCollectorEntity;
import com.isiweekloan.entity.LoanCollectorStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LoanCollectorStatusRepository extends JpaRepository<LoanCollectorStatusEntity, Long>, JpaSpecificationExecutor<LoanCollectorStatusEntity> {
}