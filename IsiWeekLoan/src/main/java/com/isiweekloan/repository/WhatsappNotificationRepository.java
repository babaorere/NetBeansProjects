package com.isiweekloan.repository;

import com.isiweekloan.entity.WhatsappNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhatsappNotificationRepository extends JpaRepository<WhatsappNotificationEntity, Long> {
    // You can add custom query methods if needed
}
