package com.isiweekloan.service;

import com.isiweekloan.entity.PaymentDatailsEntity;
import com.isiweekloan.repository.PaymentDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentDetailsService {

    private final PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    public PaymentDetailsService(PaymentDetailsRepository paymentDetailsRepository) {
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

    @Transactional(readOnly = true)
    public List<PaymentDatailsEntity> getAllPaymentDetails() {
        return paymentDetailsRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<PaymentDatailsEntity> getPaymentDetailsById(Long id) {
        return paymentDetailsRepository.findById(id);
    }

    @Transactional
    public PaymentDatailsEntity createPaymentDetails(PaymentDatailsEntity paymentDatailsEntity) {
        validatePaymentDetails(paymentDatailsEntity);
        return paymentDetailsRepository.save(paymentDatailsEntity);
    }

    @Transactional
    public Optional<PaymentDatailsEntity> updatePaymentDetails(Long id, PaymentDatailsEntity paymentDatailsEntity) {
        Objects.requireNonNull(id, "Payment Details ID cannot be null");
        validatePaymentDetails(paymentDatailsEntity);

        return paymentDetailsRepository.findById(id)
                .map(existingPaymentDetails -> {
                    // Update fields as needed
                    existingPaymentDetails.setPaymentDate(paymentDatailsEntity.getPaymentDate());
                    existingPaymentDetails.setLoanContract(paymentDatailsEntity.getLoanContract());
                    existingPaymentDetails.setPaymentStatus(paymentDatailsEntity.getPaymentStatus());
                    existingPaymentDetails.setPaymentType(paymentDatailsEntity.getPaymentType());
                    // Update other fields similarly

                    return paymentDetailsRepository.save(existingPaymentDetails);
                });
    }

    @Transactional
    public boolean deletePaymentDetails(Long id) {
        Objects.requireNonNull(id, "Payment Details ID cannot be null");

        if (paymentDetailsRepository.existsById(id)) {
            paymentDetailsRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private void validatePaymentDetails(PaymentDatailsEntity paymentDatailsEntity) {
        // Implement validation logic based on your requirements
        // For example, check for null values, required attributes, etc.
    }
}
