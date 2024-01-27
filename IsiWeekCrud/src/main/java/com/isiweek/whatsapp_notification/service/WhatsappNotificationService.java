package com.isiweek.whatsapp_notification.service;

import com.isiweek.loan_contract.domain.LoanContract;
import com.isiweek.loan_contract.repos.LoanContractRepository;
import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.whatsapp_notification.domain.WhatsappNotification;
import com.isiweek.whatsapp_notification.model.WhatsappNotificationDTO;
import com.isiweek.whatsapp_notification.repos.WhatsappNotificationRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class WhatsappNotificationService {

    private final WhatsappNotificationRepository whatsappNotificationRepository;
    private final PersonRepository personRepository;
    private final LoanContractRepository loanContractRepository;

    public WhatsappNotificationService(
            final WhatsappNotificationRepository whatsappNotificationRepository,
            final PersonRepository personRepository,
            final LoanContractRepository loanContractRepository) {
        this.whatsappNotificationRepository = whatsappNotificationRepository;
        this.personRepository = personRepository;
        this.loanContractRepository = loanContractRepository;
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
        whatsappNotificationDTO.setSubject(whatsappNotification.getSubject());
        whatsappNotificationDTO.setSentAt(whatsappNotification.getSentAt());
        whatsappNotificationDTO.setBody(whatsappNotification.getBody());
        whatsappNotificationDTO.setDateSent(whatsappNotification.getDateSent());
        whatsappNotificationDTO.setPerson(whatsappNotification.getPerson() == null ? null : whatsappNotification.getPerson().getId());
        whatsappNotificationDTO.setLoanContract(whatsappNotification.getLoanContract() == null ? null : whatsappNotification.getLoanContract().getId());
        whatsappNotificationDTO.setPerson(whatsappNotification.getPerson() == null ? null : whatsappNotification.getPerson().getId());
        whatsappNotificationDTO.setLoanContract(whatsappNotification.getLoanContract() == null ? null : whatsappNotification.getLoanContract().getId());
        return whatsappNotificationDTO;
    }

    private WhatsappNotification mapToEntity(final WhatsappNotificationDTO whatsappNotificationDTO,
            final WhatsappNotification whatsappNotification) {
        whatsappNotification.setSubject(whatsappNotificationDTO.getSubject());
        whatsappNotification.setSentAt(whatsappNotificationDTO.getSentAt());
        whatsappNotification.setBody(whatsappNotificationDTO.getBody());
        whatsappNotification.setDateSent(whatsappNotificationDTO.getDateSent());
        final Person person = whatsappNotificationDTO.getPerson() == null ? null : personRepository.findById(whatsappNotificationDTO.getPerson())
                .orElseThrow(() -> new NotFoundException("person not found"));
        whatsappNotification.setPerson(person);
        final LoanContract loanContract = whatsappNotificationDTO.getLoanContract() == null ? null : loanContractRepository.findById(whatsappNotificationDTO.getLoanContract())
                .orElseThrow(() -> new NotFoundException("loanContract not found"));
        whatsappNotification.setLoanContract(loanContract);
        return whatsappNotification;
    }

}
