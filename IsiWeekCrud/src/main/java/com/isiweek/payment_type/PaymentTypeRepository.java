package com.isiweek.payment_type;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

    boolean existsByNameIgnoreCase(String name);

}
