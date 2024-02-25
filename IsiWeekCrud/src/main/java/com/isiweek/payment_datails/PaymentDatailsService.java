package com.isiweek.payment_datails;

import com.isiweek.loan_contract.LoanContract;
import com.isiweek.loan_contract.LoanContractRepository;
import com.isiweek.payment_status.PaymentStatus;
import com.isiweek.payment_status.PaymentStatusRepository;
import com.isiweek.payment_type.PaymentType;
import com.isiweek.payment_type.PaymentTypeRepository;
import com.isiweek.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PaymentDatailsService {

    private final PaymentDatailsRepository paymentDatailsRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final LoanContractRepository loanContractRepository;
    private final PaymentStatusRepository paymentStatusRepository;

    public PaymentDatailsService(final PaymentDatailsRepository paymentDatailsRepository,
            final PaymentTypeRepository paymentTypeRepository,
            final LoanContractRepository loanContractRepository,
            final PaymentStatusRepository paymentStatusRepository) {
        this.paymentDatailsRepository = paymentDatailsRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.loanContractRepository = loanContractRepository;
        this.paymentStatusRepository = paymentStatusRepository;
    }

    public List<PaymentDatailsDTO> findAll() {
        final List<PaymentDatails> paymentDatailses = paymentDatailsRepository.findAll(Sort.by("id"));
        return paymentDatailses.stream()
                .map(paymentDatails -> mapToDTO(paymentDatails, new PaymentDatailsDTO()))
                .toList();
    }

    public PaymentDatailsDTO get(final Long id) {
        return paymentDatailsRepository.findById(id)
                .map(paymentDatails -> mapToDTO(paymentDatails, new PaymentDatailsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PaymentDatailsDTO paymentDatailsDTO) {
        final PaymentDatails paymentDatails = new PaymentDatails();
        mapToEntity(paymentDatailsDTO, paymentDatails);
        return paymentDatailsRepository.save(paymentDatails).getId();
    }

    public void update(final Long id, final PaymentDatailsDTO paymentDatailsDTO) {
        final PaymentDatails paymentDatails = paymentDatailsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(paymentDatailsDTO, paymentDatails);
        paymentDatailsRepository.save(paymentDatails);
    }

    public void delete(final Long id) {
        paymentDatailsRepository.deleteById(id);
    }

    private PaymentDatailsDTO mapToDTO(final PaymentDatails paymentDatails,
            final PaymentDatailsDTO paymentDatailsDTO) {
        paymentDatailsDTO.setId(paymentDatails.getId());
        paymentDatailsDTO.setPaymentAmount(paymentDatails.getPaymentAmount());
        paymentDatailsDTO.setPaymentDate(paymentDatails.getPaymentDate());
        paymentDatailsDTO.setDateCreated(paymentDatails.getDateCreated());
        paymentDatailsDTO.setLastUpdated(paymentDatails.getLastUpdated());
        paymentDatailsDTO.setNotes(paymentDatails.getNotes());
        paymentDatailsDTO.setPaymentType(paymentDatails.getPaymentType() == null ? null : paymentDatails.getPaymentType().getId());
        paymentDatailsDTO.setLoanContract(paymentDatails.getLoanContract() == null ? null : paymentDatails.getLoanContract().getId());
        paymentDatailsDTO.setPaymentStatus(paymentDatails.getPaymentStatus() == null ? null : paymentDatails.getPaymentStatus().getId());
        return paymentDatailsDTO;
    }

    private PaymentDatails mapToEntity(final PaymentDatailsDTO paymentDatailsDTO,
            final PaymentDatails paymentDatails) {
        paymentDatails.setPaymentAmount(paymentDatailsDTO.getPaymentAmount());
        paymentDatails.setPaymentDate(paymentDatailsDTO.getPaymentDate());
        paymentDatails.setDateCreated(paymentDatailsDTO.getDateCreated());
        paymentDatails.setLastUpdated(paymentDatailsDTO.getLastUpdated());
        paymentDatails.setNotes(paymentDatailsDTO.getNotes());
        final PaymentType paymentType = paymentDatailsDTO.getPaymentType() == null ? null : paymentTypeRepository.findById(paymentDatailsDTO.getPaymentType())
                .orElseThrow(() -> new NotFoundException("paymentType not found"));
        paymentDatails.setPaymentType(paymentType);
        final LoanContract loanContract = paymentDatailsDTO.getLoanContract() == null ? null : loanContractRepository.findById(paymentDatailsDTO.getLoanContract())
                .orElseThrow(() -> new NotFoundException("loanContract not found"));
        paymentDatails.setLoanContract(loanContract);
        final PaymentStatus paymentStatus = paymentDatailsDTO.getPaymentStatus() == null ? null : paymentStatusRepository.findById(paymentDatailsDTO.getPaymentStatus())
                .orElseThrow(() -> new NotFoundException("paymentStatus not found"));
        paymentDatails.setPaymentStatus(paymentStatus);
        return paymentDatails;
    }

}
