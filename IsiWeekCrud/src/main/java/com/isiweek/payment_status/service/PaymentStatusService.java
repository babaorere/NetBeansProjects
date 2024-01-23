package com.isiweek.payment_status.service;

import com.isiweek.payment_datails.domain.PaymentDatails;
import com.isiweek.payment_datails.repos.PaymentDatailsRepository;
import com.isiweek.payment_status.domain.PaymentStatus;
import com.isiweek.payment_status.model.PaymentStatusDTO;
import com.isiweek.payment_status.repos.PaymentStatusRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PaymentStatusService {

    private final PaymentStatusRepository paymentStatusRepository;
    private final PaymentDatailsRepository paymentDatailsRepository;

    public PaymentStatusService(final PaymentStatusRepository paymentStatusRepository,
            final PaymentDatailsRepository paymentDatailsRepository) {
        this.paymentStatusRepository = paymentStatusRepository;
        this.paymentDatailsRepository = paymentDatailsRepository;
    }

    public List<PaymentStatusDTO> findAll() {
        final List<PaymentStatus> paymentStatuses = paymentStatusRepository.findAll(Sort.by("id"));
        return paymentStatuses.stream()
                .map(paymentStatus -> mapToDTO(paymentStatus, new PaymentStatusDTO()))
                .toList();
    }

    public PaymentStatusDTO get(final Long id) {
        return paymentStatusRepository.findById(id)
                .map(paymentStatus -> mapToDTO(paymentStatus, new PaymentStatusDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PaymentStatusDTO paymentStatusDTO) {
        final PaymentStatus paymentStatus = new PaymentStatus();
        mapToEntity(paymentStatusDTO, paymentStatus);
        return paymentStatusRepository.save(paymentStatus).getId();
    }

    public void update(final Long id, final PaymentStatusDTO paymentStatusDTO) {
        final PaymentStatus paymentStatus = paymentStatusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(paymentStatusDTO, paymentStatus);
        paymentStatusRepository.save(paymentStatus);
    }

    public void delete(final Long id) {
        paymentStatusRepository.deleteById(id);
    }

    private PaymentStatusDTO mapToDTO(final PaymentStatus paymentStatus,
            final PaymentStatusDTO paymentStatusDTO) {
        paymentStatusDTO.setId(paymentStatus.getId());
        paymentStatusDTO.setName(paymentStatus.getName());
        paymentStatusDTO.setDescription(paymentStatus.getDescription());
        return paymentStatusDTO;
    }

    private PaymentStatus mapToEntity(final PaymentStatusDTO paymentStatusDTO,
            final PaymentStatus paymentStatus) {
        paymentStatus.setName(paymentStatusDTO.getName());
        paymentStatus.setDescription(paymentStatusDTO.getDescription());
        return paymentStatus;
    }

    public boolean nameExists(final String name) {
        return paymentStatusRepository.existsByNameIgnoreCase(name);
    }

    public String getReferencedWarning(final Long id) {
        final PaymentStatus paymentStatus = paymentStatusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final PaymentDatails paymentStatusPaymentDatails = paymentDatailsRepository.findFirstByPaymentStatus(paymentStatus);
        if (paymentStatusPaymentDatails != null) {
            return WebUtils.getMessage("paymentStatus.paymentDatails.paymentStatus.referenced", paymentStatusPaymentDatails.getId());
        }

        return null;
    }

}
