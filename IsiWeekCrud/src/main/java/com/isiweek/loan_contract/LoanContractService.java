package com.isiweek.loan_contract;

import com.isiweek.email_notification.EmailNotification;
import com.isiweek.email_notification.EmailNotificationRepository;
import com.isiweek.lender.Lender;
import com.isiweek.lender.LenderRepository;
import com.isiweek.loan_status.LoanStatus;
import com.isiweek.loan_status.LoanStatusRepository;
import com.isiweek.loan_type.LoanType;
import com.isiweek.loan_type.LoanTypeRepository;
import com.isiweek.payment_datails.PaymentDatails;
import com.isiweek.payment_datails.PaymentDatailsRepository;
import com.isiweek.phone_notification.PhoneNotification;
import com.isiweek.phone_notification.PhoneNotificationRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import com.isiweek.whatsapp_notification.WhatsappNotification;
import com.isiweek.whatsapp_notification.WhatsappNotificationRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LoanContractService {

    private final LoanContractRepository loanContractRepository;
    private final LenderRepository lenderRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final LoanStatusRepository loanStatusRepository;
    private final EmailNotificationRepository emailNotificationRepository;
    private final PaymentDatailsRepository paymentDatailsRepository;
    private final PhoneNotificationRepository phoneNotificationRepository;
    private final WhatsappNotificationRepository whatsappNotificationRepository;

    public LoanContractService(final LoanContractRepository loanContractRepository,
            final LenderRepository lenderRepository, final LoanTypeRepository loanTypeRepository,
            final LoanStatusRepository loanStatusRepository,
            final EmailNotificationRepository emailNotificationRepository,
            final PaymentDatailsRepository paymentDatailsRepository,
            final PhoneNotificationRepository phoneNotificationRepository,
            final WhatsappNotificationRepository whatsappNotificationRepository) {
        this.loanContractRepository = loanContractRepository;
        this.lenderRepository = lenderRepository;
        this.loanTypeRepository = loanTypeRepository;
        this.loanStatusRepository = loanStatusRepository;
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
        loanContractDTO.setDate(loanContract.getDate());
        loanContractDTO.setDateOfMaturity(loanContract.getDateOfMaturity());
        loanContractDTO.setDefaultInterestRate(loanContract.getDefaultInterestRate());
        loanContractDTO.setInterestRate(loanContract.getInterestRate());
        loanContractDTO.setLoanAmount(loanContract.getLoanAmount());
        loanContractDTO.setPayment(loanContract.getPayment());
        loanContractDTO.setPrepaymentPenalty(loanContract.getPrepaymentPenalty());
        loanContractDTO.setDateCreated(loanContract.getDateCreated());
        loanContractDTO.setLastUpdated(loanContract.getLastUpdated());
        loanContractDTO.setLoanPurpose(loanContract.getLoanPurpose());
        loanContractDTO.setLoanTerm(loanContract.getLoanTerm());
        loanContractDTO.setCollateral(loanContract.getCollateral());
        loanContractDTO.setCustomer(loanContract.getCustomer() == null ? null : loanContract.getCustomer().getId());
        loanContractDTO.setLoanType(loanContract.getLoanType() == null ? null : loanContract.getLoanType().getId());
        loanContractDTO.setLoanStatus(loanContract.getLoanStatus() == null ? null : loanContract.getLoanStatus().getId());
        return loanContractDTO;
    }

    private LoanContract mapToEntity(final LoanContractDTO loanContractDTO,
            final LoanContract loanContract) {
        loanContract.setDate(loanContractDTO.getDate());
        loanContract.setDateOfMaturity(loanContractDTO.getDateOfMaturity());
        loanContract.setDefaultInterestRate(loanContractDTO.getDefaultInterestRate());
        loanContract.setInterestRate(loanContractDTO.getInterestRate());
        loanContract.setLoanAmount(loanContractDTO.getLoanAmount());
        loanContract.setPayment(loanContractDTO.getPayment());
        loanContract.setPrepaymentPenalty(loanContractDTO.getPrepaymentPenalty());
        loanContract.setDateCreated(loanContractDTO.getDateCreated());
        loanContract.setLastUpdated(loanContractDTO.getLastUpdated());
        loanContract.setLoanPurpose(loanContractDTO.getLoanPurpose());
        loanContract.setLoanTerm(loanContractDTO.getLoanTerm());
        loanContract.setCollateral(loanContractDTO.getCollateral());
        final Lender customer = loanContractDTO.getCustomer() == null ? null : lenderRepository.findById(loanContractDTO.getCustomer())
                .orElseThrow(() -> new NotFoundException("customer not found"));
        loanContract.setCustomer(customer);
        final LoanType loanType = loanContractDTO.getLoanType() == null ? null : loanTypeRepository.findById(loanContractDTO.getLoanType())
                .orElseThrow(() -> new NotFoundException("loanType not found"));
        loanContract.setLoanType(loanType);
        final LoanStatus loanStatus = loanContractDTO.getLoanStatus() == null ? null : loanStatusRepository.findById(loanContractDTO.getLoanStatus())
                .orElseThrow(() -> new NotFoundException("loanStatus not found"));
        loanContract.setLoanStatus(loanStatus);
        return loanContract;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final LoanContract loanContract = loanContractRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final EmailNotification loanContractEmailNotification = emailNotificationRepository.findFirstByLoanContract(loanContract);
        if (loanContractEmailNotification != null) {
            referencedWarning.setKey("loanContract.emailNotification.loanContract.referenced");
            referencedWarning.addParam(loanContractEmailNotification.getId());
            return referencedWarning;
        }
        final PaymentDatails loanContractPaymentDatails = paymentDatailsRepository.findFirstByLoanContract(loanContract);
        if (loanContractPaymentDatails != null) {
            referencedWarning.setKey("loanContract.paymentDatails.loanContract.referenced");
            referencedWarning.addParam(loanContractPaymentDatails.getId());
            return referencedWarning;
        }
        final PhoneNotification loanContractPhoneNotification = phoneNotificationRepository.findFirstByLoanContract(loanContract);
        if (loanContractPhoneNotification != null) {
            referencedWarning.setKey("loanContract.phoneNotification.loanContract.referenced");
            referencedWarning.addParam(loanContractPhoneNotification.getId());
            return referencedWarning;
        }
        final WhatsappNotification loanContractWhatsappNotification = whatsappNotificationRepository.findFirstByLoanContract(loanContract);
        if (loanContractWhatsappNotification != null) {
            referencedWarning.setKey("loanContract.whatsappNotification.loanContract.referenced");
            referencedWarning.addParam(loanContractWhatsappNotification.getId());
            return referencedWarning;
        }
        return null;
    }

}
