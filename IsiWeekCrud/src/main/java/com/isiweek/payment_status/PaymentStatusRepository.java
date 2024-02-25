package com.isiweek.payment_status;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {

    boolean existsByNameIgnoreCase(String name);

}
