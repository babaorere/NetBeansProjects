package com.isiweek.payment_status.repos;

import com.isiweek.payment_status.domain.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {

    boolean existsByNameIgnoreCase(String name);

}
