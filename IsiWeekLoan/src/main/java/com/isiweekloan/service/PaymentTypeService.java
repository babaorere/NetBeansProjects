package com.isiweekloan.service;

import com.isiweekloan.entity.PaymentTypeEntity;
import com.isiweekloan.repository.PaymentTypeRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;

    @Autowired
    public PaymentTypeService(PaymentTypeRepository paymentTypeRepository) {
        this.paymentTypeRepository = paymentTypeRepository;
    }

    @Transactional(readOnly = true)
    public List<PaymentTypeEntity> getAllPaymentTypes() {
        return paymentTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<PaymentTypeEntity> getPaymentTypeById(Long id) {
        return paymentTypeRepository.findById(id);
    }

    @Transactional
    public PaymentTypeEntity createPaymentType(PaymentTypeEntity paymentTypeEntity) {
        validatePaymentType(paymentTypeEntity);
        return paymentTypeRepository.save(paymentTypeEntity);
    }

    @Transactional
    public Optional<PaymentTypeEntity> updatePaymentType(Long id, PaymentTypeEntity paymentTypeEntity) {
        Objects.requireNonNull(id, "Payment Type ID cannot be null");
        validatePaymentType(paymentTypeEntity);

        return paymentTypeRepository.findById(id)
                .map(existingPaymentType -> {
                    // Update fields as needed
                    existingPaymentType.setName(paymentTypeEntity.getName());
                    existingPaymentType.setDescription(paymentTypeEntity.getDescription());
                    existingPaymentType.setProcessingFee(paymentTypeEntity.getProcessingFee());
                    existingPaymentType.setSupportedCurrencies(paymentTypeEntity.getSupportedCurrencies());
                    // Update other fields similarly

                    return paymentTypeRepository.save(existingPaymentType);
                });
    }

    @Transactional
    public boolean deletePaymentType(Long id) {
        Objects.requireNonNull(id, "Payment Type ID cannot be null");

        if (paymentTypeRepository.existsById(id)) {
            paymentTypeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private void validatePaymentType(PaymentTypeEntity paymentTypeEntity) {
        // Implement validation logic based on your requirements
        // For example, check for null values, required attributes, etc.
    }
}
