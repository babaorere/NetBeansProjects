package com.isiweek.payment_type.service;

import com.isiweek.payment_datails.domain.PaymentDatails;
import com.isiweek.payment_datails.repos.PaymentDatailsRepository;
import com.isiweek.payment_type.domain.PaymentType;
import com.isiweek.payment_type.model.PaymentTypeDTO;
import com.isiweek.payment_type.repos.PaymentTypeRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;
    private final PaymentDatailsRepository paymentDatailsRepository;

    public PaymentTypeService(final PaymentTypeRepository paymentTypeRepository,
            final PaymentDatailsRepository paymentDatailsRepository) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.paymentDatailsRepository = paymentDatailsRepository;
    }

    public List<PaymentTypeDTO> findAll() {
        final List<PaymentType> paymentTypes = paymentTypeRepository.findAll(Sort.by("id"));
        return paymentTypes.stream()
                .map(paymentType -> mapToDTO(paymentType, new PaymentTypeDTO()))
                .toList();
    }

    public PaymentTypeDTO get(final Long id) {
        return paymentTypeRepository.findById(id)
                .map(paymentType -> mapToDTO(paymentType, new PaymentTypeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PaymentTypeDTO paymentTypeDTO) {
        final PaymentType paymentType = new PaymentType();
        mapToEntity(paymentTypeDTO, paymentType);
        return paymentTypeRepository.save(paymentType).getId();
    }

    public void update(final Long id, final PaymentTypeDTO paymentTypeDTO) {
        final PaymentType paymentType = paymentTypeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(paymentTypeDTO, paymentType);
        paymentTypeRepository.save(paymentType);
    }

    public void delete(final Long id) {
        paymentTypeRepository.deleteById(id);
    }

    private PaymentTypeDTO mapToDTO(final PaymentType paymentType,
            final PaymentTypeDTO paymentTypeDTO) {
        paymentTypeDTO.setId(paymentType.getId());
        paymentTypeDTO.setName(paymentType.getName());
        paymentTypeDTO.setDescription(paymentType.getDescription());
        paymentTypeDTO.setProcessingFee(paymentType.getProcessingFee());
        paymentTypeDTO.setSupportedCurrencies(paymentType.getSupportedCurrencies());
        return paymentTypeDTO;
    }

    private PaymentType mapToEntity(final PaymentTypeDTO paymentTypeDTO,
            final PaymentType paymentType) {
        paymentType.setName(paymentTypeDTO.getName());
        paymentType.setDescription(paymentTypeDTO.getDescription());
        paymentType.setProcessingFee(paymentTypeDTO.getProcessingFee());
        paymentType.setSupportedCurrencies(paymentTypeDTO.getSupportedCurrencies());
        return paymentType;
    }

    public boolean nameExists(final String name) {
        return paymentTypeRepository.existsByNameIgnoreCase(name);
    }

    public String getReferencedWarning(final Long id) {
        final PaymentType paymentType = paymentTypeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final PaymentDatails paymentTypePaymentDatails = paymentDatailsRepository.findFirstByPaymentType(paymentType);
        if (paymentTypePaymentDatails != null) {
            return WebUtils.getMessage("paymentType.paymentDatails.paymentType.referenced", paymentTypePaymentDatails.getId());
        }

        return null;
    }

}
