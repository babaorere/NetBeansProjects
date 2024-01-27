package com.isiweekloan.repository;

import com.isiweekloan.entity.PhoneNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface PhoneNotificationRepository extends JpaRepository<PhoneNotificationEntity, Long>, JpaSpecificationExecutor<PhoneNotificationEntity> {
}