package com.isiweekloan.repository;

import com.isiweekloan.entity.LoanContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanContractRepository extends JpaRepository<LoanContractEntity, Long>, JpaSpecificationExecutor<LoanContractEntity> {
}