package com.isiweekloan.repository;

import com.isiweekloan.entity.PaymentDatailsEntity;
import com.isiweekloan.entity.PaymentStatusEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatusEntity, Long> {

    // Optional method to find by name (example)
    Optional<PaymentStatusEntity> findByName(String name);

    // Custom query example using JPQL:
    @Query("SELECT pd FROM PaymentDatailsEntity pd WHERE pd.paymentStatus.name = :paymentStatusName")
    List<PaymentDatailsEntity> findAllPaymentsByStatusName(String paymentStatusName);

    // You can add additional custom queries based on your needs
}
