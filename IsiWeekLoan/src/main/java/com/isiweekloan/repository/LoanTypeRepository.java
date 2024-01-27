package com.isiweekloan.repository;

import com.isiweekloan.entity.LoanContractEntity;
import com.isiweekloan.entity.LoanTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LoanTypeRepository extends JpaRepository<LoanTypeEntity, Long>, JpaSpecificationExecutor<LoanTypeEntity> {
}