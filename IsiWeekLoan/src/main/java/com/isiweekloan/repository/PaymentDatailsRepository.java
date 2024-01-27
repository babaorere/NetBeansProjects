package com.isiweekloan.repository;

import com.isiweekloan.entity.PaymentDatailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface PaymentDatailsRepository extends JpaRepository<PaymentDatailsEntity, Long>, JpaSpecificationExecutor<PaymentDatailsEntity> {
}