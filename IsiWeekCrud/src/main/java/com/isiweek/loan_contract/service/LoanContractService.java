package com.isiweek.loan_contract.service;

import com.isiweek.customer.domain.Customer;
import com.isiweek.customer.repos.CustomerRepository;
import com.isiweek.email_notification.domain.EmailNotification;
import com.isiweek.email_notification.repos.EmailNotificationRepository;
import com.isiweek.loan_contract.domain.LoanContract;
import com.isiweek.loan_contract.model.LoanContractDTO;
import com.isiweek.loan_contract.repos.LoanContractRepository;
import com.isiweek.loan_status.domain.LoanStatus;
import com.isiweek.loan_status.repos.LoanStatusRepository;
import com.isiweek.loan_type.domain.LoanType;
import com.isiweek.loan_type.repos.LoanTypeRepository;
import com.isiweek.payment_datails.domain.PaymentDatails;
import com.isiweek.payment_datails.repos.PaymentDatailsRepository;
import com.isiweek.phone_notification.domain.PhoneNotification;
import com.isiweek.phone_notification.repos.PhoneNotificationRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.WebUtils;
import com.isiweek.whatsapp_notification.domain.WhatsappNotification;
import com.isiweek.whatsapp_notification.repos.WhatsappNotificationRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class LoanContractService {

    private final LoanContractRepository loanContractRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final LoanStatusRepository loanStatusRepository;
    private final CustomerRepository customerRepository;
    private final EmailNotificationRepository emailNotificationRepository;
    private final PaymentDatailsRepository paymentDatailsRepository;
    private final PhoneNotificationRepository phoneNotificationRepository;
    private final WhatsappNotificationRepository whatsappNotificationRepository;

    public LoanContractService(final LoanContractRepository loanContractRepository,
            final LoanTypeRepository loanTypeRepository,
            final LoanStatusRepository loanStatusRepository,
            final CustomerRepository customerRepository,
            final EmailNotificationRepository emailNotificationRepository,
            final PaymentDatailsRepository paymentDatailsRepository,
            final PhoneNotificationRepository phoneNotificationRepository,
            final WhatsappNotificationRepository whatsappNotificationRepository) {
        this.loanContractRepository = loanContractRepository;
        this.loanTypeRepository = loanTypeRepository;
        this.loanStatusRepository = loanStatusRepository;
        this.customerRepository = customerRepository;
        this.emailNotificationRepository = emailNotificationRepository;
        this.paymentDatailsRepository = paymentDatailsRepository;
        this.phoneNotificationRepository = phoneNotificationRepository;
        this.whatsappNotificationRepository = whatsappNotificationRepository;
    }

    public List<LoanContractDTO> findAll() {
        final List<LoanContract> loanContracts = loanContractRepository.findAll(Sort.by("id"));
        return loanContracts.stream()
                .map(loanContract -> mapToDTO(loanContract, new LoanContractDTO()))
                .toList();
    }

    public LoanContractDTO get(final Long id) {
        return loanContractRepository.findById(id)
                .map(loanContract -> mapToDTO(loanContract, new LoanContractDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LoanContractDTO loanContractDTO) {
        final LoanContract loanContract = new LoanContract();
        mapToEntity(loanContractDTO, loanContract);
        return loanContractRepository.save(loanContract).getId();
    }

    public void update(final Long id, final LoanContractDTO loanContractDTO) {
        final LoanContract loanContract = loanContractRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(loanContractDTO, loanContract);
        loanContractRepository.save(loanContract);
    }

    public void delete(final Long id) {
        loanContractRepository.deleteById(id);
    }

    private LoanContractDTO mapToDTO(final LoanContract loanContract,
            final LoanContractDTO loanContractDTO) {
        loanContractDTO.setId(loanContract.getId());
        loanContractDTO.setLoanAmount(loanContract.getLoanAmount());
        loanContractDTO.setInterestRate(loanContract.getInterestRate());
        loanContractDTO.setLoanTerm(loanContract.getLoanTerm());
        loanContractDTO.setPayment(loanContract.getPayment());
        loanContractDTO.setDate(loanContract.getDate());
        loanContractDTO.setDateOfMaturity(loanContract.getDateOfMaturity());
        loanContractDTO.setLoanPurpose(loanContract.getLoanPurpose());
        loanContractDTO.setCollateral(loanContract.getCollateral());
        loanContractDTO.setPrepaymentPenalty(loanContract.getPrepaymentPenalty());
        loanContractDTO.setDefaultInterestRate(loanContract.getDefaultInterestRate());
        loanContractDTO.setLoanType(loanContract.getLoanType() == null ? null : loanContract.getLoanType().getId());
        loanContractDTO.setLoanStatus(loanContract.getLoanStatus() == null ? null : loanContract.getLoanStatus().getId());
        loanContractDTO.setCustomer(loanContract.getCustomer() == null ? null : loanContract.getCustomer().getId());
        loanContractDTO.setCustomer(loanContract.getCustomer() == null ? null : loanContract.getCustomer().getId());
        loanContractDTO.setLoanStatus(loanContract.getLoanStatus() == null ? null : loanContract.getLoanStatus().getId());
        loanContractDTO.setLoanType(loanContract.getLoanType() == null ? null : loanContract.getLoanType().getId());
        return loanContractDTO;
    }

    private LoanContract mapToEntity(final LoanContractDTO loanContractDTO,
            final LoanContract loanContract) {
        loanContract.setLoanAmount(loanContractDTO.getLoanAmount());
        loanContract.setInterestRate(loanContractDTO.getInterestRate());
        loanContract.setLoanTerm(loanContractDTO.getLoanTerm());
        loanContract.setPayment(loanContractDTO.getPayment());
        loanContract.setDate(loanContractDTO.getDate());
        loanContract.setDateOfMaturity(loanContractDTO.getDateOfMaturity());
        loanContract.setLoanPurpose(loanContractDTO.getLoanPurpose());
        loanContract.setCollateral(loanContractDTO.getCollateral());
        loanContract.setPrepaymentPenalty(loanContractDTO.getPrepaymentPenalty());
        loanContract.setDefaultInterestRate(loanContractDTO.getDefaultInterestRate());

        final LoanType loanType = loanContractDTO.getLoanType() == null ? null : loanTypeRepository.findById(loanContractDTO.getLoanType())
                .orElseThrow(() -> new NotFoundException("loanType not found"));
        loanContract.setLoanType(loanType);
        final LoanStatus loanStatus = loanContractDTO.getLoanStatus() == null ? null : loanStatusRepository.findById(loanContractDTO.getLoanStatus())
                .orElseThrow(() -> new NotFoundException("loanStatus not found"));
        loanContract.setLoanStatus(loanStatus);
        final Customer customer = loanContractDTO.getCustomer() == null ? null : customerRepository.findById(loanContractDTO.getCustomer())
                .orElseThrow(() -> new NotFoundException("customer not found"));
        loanContract.setCustomer(customer);
        return loanContract;
    }

    public String getReferencedWarning(final Long id) {
        final LoanContract loanContract = loanContractRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        final EmailNotification loanContractEmailNotification = emailNotificationRepository.findFirstByLoanContract(loanContract);

        if (loanContractEmailNotification != null) {
            return WebUtils.getMessage("loanContract.emailNotification.loanContract.referenced", loanContractEmailNotification.getId());
        }

        final PaymentDatails loanContractPaymentDatails = paymentDatailsRepository.findFirstByLoanContract(loanContract);
        if (loanContractPaymentDatails != null) {
            return WebUtils.getMessage("loanContract.paymentDatails.loanContract.referenced", loanContractPaymentDatails.getId());
        }

        final PhoneNotification loanContractPhoneNotification = phoneNotificationRepository.findFirstByLoanContract(loanContract);
        if (loanContractPhoneNotification != null) {
            return WebUtils.getMessage("loanContract.phoneNotification.loanContract.referenced", loanContractPhoneNotification.getId());
        }

        final WhatsappNotification loanContractWhatsappNotification = whatsappNotificationRepository.findFirstByLoanContract(loanContract);
        if (loanContractWhatsappNotification != null) {
            return WebUtils.getMessage("loanContract.whatsappNotification.loanContract.referenced", loanContractWhatsappNotification.getId());
        }

        return null;
    }

}
