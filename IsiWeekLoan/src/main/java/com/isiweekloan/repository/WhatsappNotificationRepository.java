package com.isiweekloan.repository;

import com.isiweekloan.entity.WhatsappNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface WhatsappNotificationRepository extends JpaRepository<WhatsappNotificationEntity, Long>, JpaSpecificationExecutor<WhatsappNotificationEntity> {
}