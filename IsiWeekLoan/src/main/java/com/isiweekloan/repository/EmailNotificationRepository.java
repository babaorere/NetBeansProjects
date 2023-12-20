package com.isiweekloan.repository;

import com.isiweekloan.entity.EmailNotificationEntity;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailNotificationRepository extends JpaRepository<EmailNotificationEntity, Long> {

    // Optional method to find by subject (example)
    Optional<EmailNotificationEntity> findBySubject(String subject);

    // Find email notifications sent to a specific person (optional)
    @Query("SELECT e FROM EmailNotificationEntity e WHERE e.person.id = :personId")
    List<EmailNotificationEntity> findByPersonId(Long personId);

    // Custom query example using JPQL and TemporalType annotation:
    @Query("SELECT e FROM EmailNotificationEntity e WHERE e.dateSent >= :from AND e.dateSent < :to")
    List<EmailNotificationEntity> findBetweenDates(Date from, Date to);

    // You can add additional custom query methods here based on your needs
}
