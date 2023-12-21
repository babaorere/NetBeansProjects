package com.isiweekloan.repository;

import com.isiweekloan.entity.PhoneNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNotificationRepository extends JpaRepository<PhoneNotificationEntity, Long> {
    // Add custom query methods if needed
}
