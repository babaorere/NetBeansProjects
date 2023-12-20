package com.isiweekloan.repository;

import com.isiweekloan.entity.PaymentDatailsEntity;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDatailsRepository extends JpaRepository<PaymentDatailsEntity, Long> {

    // Optional method to find by payment date
    Optional<PaymentDatailsEntity> findByPaymentDate(Date paymentDate);

    // Custom query example using JPQL:
    @Query("SELECT pd FROM PaymentDatailsEntity pd WHERE pd.loanContract.id = :loanContractId")
    List<PaymentDatailsEntity> findAllByLoanContractId(Long loanContractId);

    // You can add additional custom queries based on your needs
}
