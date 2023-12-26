package com.isiweekloan.repository;

import com.isiweekloan.entity.EmailNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface EmailNotificationRepository extends JpaRepository<EmailNotificationEntity, Long>, JpaSpecificationExecutor<EmailNotificationEntity> {
}