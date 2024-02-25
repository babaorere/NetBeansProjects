package com.isiweek.email_notification;

import com.isiweek.loan_contract.LoanContract;
import com.isiweek.loan_contract.LoanContractRepository;
import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EmailNotificationService {

    private final EmailNotificationRepository emailNotificationRepository;
    private final LoanContractRepository loanContractRepository;
    private final PersonRepository personRepository;

    public EmailNotificationService(final EmailNotificationRepository emailNotificationRepository,
            final LoanContractRepository loanContractRepository,
            final PersonRepository personRepository) {
        this.emailNotificationRepository = emailNotificationRepository;
        this.loanContractRepository = loanContractRepository;
        this.personRepository = personRepository;
    }

    public List<EmailNotificationDTO> findAll() {
        final List<EmailNotification> emailNotifications = emailNotificationRepository.findAll(Sort.by("id"));
        return emailNotifications.stream()
                .map(emailNotification -> mapToDTO(emailNotification, new EmailNotificationDTO()))
                .toList();
    }

    public EmailNotificationDTO get(final Long id) {
        return emailNotificationRepository.findById(id)
                .map(emailNotification -> mapToDTO(emailNotification, new EmailNotificationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EmailNotificationDTO emailNotificationDTO) {
        final EmailNotification emailNotification = new EmailNotification();
        mapToEntity(emailNotificationDTO, emailNotification);
        return emailNotificationRepository.save(emailNotification).getId();
    }

    public void update(final Long id, final EmailNotificationDTO emailNotificationDTO) {
        final EmailNotification emailNotification = emailNotificationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(emailNotificationDTO, emailNotification);
        emailNotificationRepository.save(emailNotification);
    }

    public void delete(final Long id) {
        emailNotificationRepository.deleteById(id);
    }

    private EmailNotificationDTO mapToDTO(final EmailNotification emailNotification,
            final EmailNotificationDTO emailNotificationDTO) {
        emailNotificationDTO.setId(emailNotification.getId());
        emailNotificationDTO.setDateCreated(emailNotification.getDateCreated());
        emailNotificationDTO.setDateSent(emailNotification.getDateSent());
        emailNotificationDTO.setLastUpdated(emailNotification.getLastUpdated());
        emailNotificationDTO.setSentAt(emailNotification.getSentAt());
        emailNotificationDTO.setSubject(emailNotification.getSubject());
        emailNotificationDTO.setBody(emailNotification.getBody());
        emailNotificationDTO.setLoanContract(emailNotification.getLoanContract() == null ? null : emailNotification.getLoanContract().getId());
        emailNotificationDTO.setPerson(emailNotification.getPerson() == null ? null : emailNotification.getPerson().getId());
        return emailNotificationDTO;
    }

    private EmailNotification mapToEntity(final EmailNotificationDTO emailNotificationDTO,
            final EmailNotification emailNotification) {
        emailNotification.setDateCreated(emailNotificationDTO.getDateCreated());
        emailNotification.setDateSent(emailNotificationDTO.getDateSent());
        emailNotification.setLastUpdated(emailNotificationDTO.getLastUpdated());
        emailNotification.setSentAt(emailNotificationDTO.getSentAt());
        emailNotification.setSubject(emailNotificationDTO.getSubject());
        emailNotification.setBody(emailNotificationDTO.getBody());
        final LoanContract loanContract = emailNotificationDTO.getLoanContract() == null ? null : loanContractRepository.findById(emailNotificationDTO.getLoanContract())
                .orElseThrow(() -> new NotFoundException("loanContract not found"));
        emailNotification.setLoanContract(loanContract);
        final Person person = emailNotificationDTO.getPerson() == null ? null : personRepository.findById(emailNotificationDTO.getPerson())
                .orElseThrow(() -> new NotFoundException("person not found"));
        emailNotification.setPerson(person);
        return emailNotification;
    }

}
