package com.isiweekloan.service;

import com.isiweekloan.entity.PaymentStatusEntity;
import com.isiweekloan.repository.PaymentStatusRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentStatusService {

    private final PaymentStatusRepository paymentStatusRepository;

    @Autowired
    public PaymentStatusService(PaymentStatusRepository paymentStatusRepository) {
        this.paymentStatusRepository = paymentStatusRepository;
    }

    @Transactional(readOnly = true)
    public List<PaymentStatusEntity> getAllPaymentStatus() {
        return paymentStatusRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<PaymentStatusEntity> getPaymentStatusById(Long id) {
        return paymentStatusRepository.findById(id);
    }

    @Transactional
    public PaymentStatusEntity createPaymentStatus(PaymentStatusEntity paymentStatusEntity) {
        validatePaymentStatus(paymentStatusEntity);
        return paymentStatusRepository.save(paymentStatusEntity);
    }

    @Transactional
    public Optional<PaymentStatusEntity> updatePaymentStatus(Long id, PaymentStatusEntity paymentStatusEntity) {
        Objects.requireNonNull(id, "Payment Status ID cannot be null");
        validatePaymentStatus(paymentStatusEntity);

        return paymentStatusRepository.findById(id)
                .map(existingPaymentStatus -> {
                    // Update fields as needed
                    existingPaymentStatus.setName(paymentStatusEntity.getName());
                    existingPaymentStatus.setDescription(paymentStatusEntity.getDescription());
                    // Update other fields similarly

                    return paymentStatusRepository.save(existingPaymentStatus);
                });
    }

    @Transactional
    public boolean deletePaymentStatus(Long id) {
        Objects.requireNonNull(id, "Payment Status ID cannot be null");

        if (paymentStatusRepository.existsById(id)) {
            paymentStatusRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private void validatePaymentStatus(PaymentStatusEntity paymentStatusEntity) {
        // Implement validation logic based on your requirements
        // For example, check for null values, required attributes, etc.
    }
}
