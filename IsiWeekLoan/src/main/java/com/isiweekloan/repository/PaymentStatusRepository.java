package com.isiweekloan.repository;

import com.isiweekloan.entity.PaymentDatailsEntity;
import com.isiweekloan.entity.PaymentStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatusEntity, Long>, JpaSpecificationExecutor<PaymentStatusEntity> {
}