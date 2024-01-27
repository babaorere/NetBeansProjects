package com.isiweek.payment_type.repos;

import com.isiweek.payment_type.domain.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

    boolean existsByNameIgnoreCase(String name);

}
