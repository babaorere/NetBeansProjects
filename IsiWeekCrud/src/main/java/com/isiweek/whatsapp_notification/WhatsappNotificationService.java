package com.isiweek.whatsapp_notification;

import com.isiweek.loan_contract.LoanContract;
import com.isiweek.loan_contract.LoanContractRepository;
import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class WhatsappNotificationService {

    private final WhatsappNotificationRepository whatsappNotificationRepository;
    private final LoanContractRepository loanContractRepository;
    private final PersonRepository personRepository;

    public WhatsappNotificationService(
            final WhatsappNotificationRepository whatsappNotificationRepository,
            final LoanContractRepository loanContractRepository,
            final PersonRepository personRepository) {
        this.whatsappNotificationRepository = whatsappNotificationRepository;
        this.loanContractRepository = loanContractRepository;
        this.personRepository = personRepository;
    }

    public List<WhatsappNotificationDTO> findAll() {
        final List<WhatsappNotification> whatsappNotifications = whatsappNotificationRepository.findAll(Sort.by("id"));
        return whatsappNotifications.stream()
                .map(whatsappNotification -> mapToDTO(whatsappNotification, new WhatsappNotificationDTO()))
                .toList();
    }

    public WhatsappNotificationDTO get(final Long id) {
        return whatsappNotificationRepository.findById(id)
                .map(whatsappNotification -> mapToDTO(whatsappNotification, new WhatsappNotificationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final WhatsappNotificationDTO whatsappNotificationDTO) {
        final WhatsappNotification whatsappNotification = new WhatsappNotification();
        mapToEntity(whatsappNotificationDTO, whatsappNotification);
        return whatsappNotificationRepository.save(whatsappNotification).getId();
    }

    public void update(final Long id, final WhatsappNotificationDTO whatsappNotificationDTO) {
        final WhatsappNotification whatsappNotification = whatsappNotificationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(whatsappNotificationDTO, whatsappNotification);
        whatsappNotificationRepository.save(whatsappNotification);
    }

    public void delete(final Long id) {
        whatsappNotificationRepository.deleteById(id);
    }

    private WhatsappNotificationDTO mapToDTO(final WhatsappNotification whatsappNotification,
            final WhatsappNotificationDTO whatsappNotificationDTO) {
        whatsappNotificationDTO.setId(whatsappNotification.getId());
        whatsappNotificationDTO.setDateCreated(whatsappNotification.getDateCreated());
        whatsappNotificationDTO.setDateSent(whatsappNotification.getDateSent());
        whatsappNotificationDTO.setLastUpdated(whatsappNotification.getLastUpdated());
        whatsappNotificationDTO.setSentAt(whatsappNotification.getSentAt());
        whatsappNotificationDTO.setSubject(whatsappNotification.getSubject());
        whatsappNotificationDTO.setBody(whatsappNotification.getBody());
        whatsappNotificationDTO.setLoanContract(whatsappNotification.getLoanContract() == null ? null : whatsappNotification.getLoanContract().getId());
        whatsappNotificationDTO.setPerson(whatsappNotification.getPerson() == null ? null : whatsappNotification.getPerson().getId());
        return whatsappNotificationDTO;
    }

    private WhatsappNotification mapToEntity(final WhatsappNotificationDTO whatsappNotificationDTO,
            final WhatsappNotification whatsappNotification) {
        whatsappNotification.setDateCreated(whatsappNotificationDTO.getDateCreated());
        whatsappNotification.setDateSent(whatsappNotificationDTO.getDateSent());
        whatsappNotification.setLastUpdated(whatsappNotificationDTO.getLastUpdated());
        whatsappNotification.setSentAt(whatsappNotificationDTO.getSentAt());
        whatsappNotification.setSubject(whatsappNotificationDTO.getSubject());
        whatsappNotification.setBody(whatsappNotificationDTO.getBody());
        final LoanContract loanContract = whatsappNotificationDTO.getLoanContract() == null ? null : loanContractRepository.findById(whatsappNotificationDTO.getLoanContract())
                .orElseThrow(() -> new NotFoundException("loanContract not found"));
        whatsappNotification.setLoanContract(loanContract);
        final Person person = whatsappNotificationDTO.getPerson() == null ? null : personRepository.findById(whatsappNotificationDTO.getPerson())
                .orElseThrow(() -> new NotFoundException("person not found"));
        whatsappNotification.setPerson(person);
        return whatsappNotification;
    }

}
