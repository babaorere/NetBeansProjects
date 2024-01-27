package com.isiweek.email_notification;

import com.isiweek.email_notification.EmailNotification;
import com.isiweek.email_notification.EmailNotificationDTO;
import com.isiweek.email_notification.EmailNotificationRepository;
import com.isiweek.loan_contract.domain.LoanContract;
import com.isiweek.loan_contract.repos.LoanContractRepository;
import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {

    private final EmailNotificationRepository emailNotificationRepository;
    private final PersonRepository personRepository;
    private final LoanContractRepository loanContractRepository;

    public EmailNotificationService(final EmailNotificationRepository emailNotificationRepository,
            final PersonRepository personRepository,
            final LoanContractRepository loanContractRepository) {
        this.emailNotificationRepository = emailNotificationRepository;
        this.personRepository = personRepository;
        this.loanContractRepository = loanContractRepository;
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
        emailNotificationDTO.setSubject(emailNotification.getSubject());
        emailNotificationDTO.setSentAt(emailNotification.getSentAt());
        emailNotificationDTO.setBody(emailNotification.getBody());
        emailNotificationDTO.setDateSent(emailNotification.getDateSent());
        emailNotificationDTO.setPerson(emailNotification.getPerson() == null ? null : emailNotification.getPerson().getId());
        emailNotificationDTO.setLoanContract(emailNotification.getLoanContract() == null ? null : emailNotification.getLoanContract().getId());
        emailNotificationDTO.setPerson(emailNotification.getPerson() == null ? null : emailNotification.getPerson().getId());
        emailNotificationDTO.setLoanContract(emailNotification.getLoanContract() == null ? null : emailNotification.getLoanContract().getId());
        return emailNotificationDTO;
    }

    private EmailNotification mapToEntity(final EmailNotificationDTO emailNotificationDTO,
            final EmailNotification emailNotification) {
        emailNotification.setSubject(emailNotificationDTO.getSubject());
        emailNotification.setSentAt(emailNotificationDTO.getSentAt());
        emailNotification.setBody(emailNotificationDTO.getBody());
        emailNotification.setDateSent(emailNotificationDTO.getDateSent());
        final Person person = emailNotificationDTO.getPerson() == null ? null : personRepository.findById(emailNotificationDTO.getPerson())
                .orElseThrow(() -> new NotFoundException("person not found"));
        emailNotification.setPerson(person);
        final LoanContract loanContract = emailNotificationDTO.getLoanContract() == null ? null : loanContractRepository.findById(emailNotificationDTO.getLoanContract())
                .orElseThrow(() -> new NotFoundException("loanContract not found"));
        emailNotification.setLoanContract(loanContract);
        return emailNotification;
    }

}
