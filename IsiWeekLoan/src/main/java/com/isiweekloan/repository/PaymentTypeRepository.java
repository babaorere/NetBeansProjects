package com.isiweekloan.repository;

import com.isiweekloan.entity.PaymentDatailsEntity;
import com.isiweekloan.entity.PaymentTypeEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentTypeEntity, Long> {

    // Optional method to find by name (example)
    Optional<PaymentTypeEntity> findByName(String name);

    // Custom query example using JPQL:
    @Query("SELECT pd FROM PaymentDatailsEntity pd WHERE pd.paymentType.name = :paymentTypeName")
    List<PaymentDatailsEntity> findAllPaymentsByType(String paymentTypeName);

    // You can add additional custom queries based on your needs
}
