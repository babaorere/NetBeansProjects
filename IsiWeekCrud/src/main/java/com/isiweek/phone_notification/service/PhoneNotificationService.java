package com.isiweek.phone_notification.service;

import com.isiweek.loan_contract.domain.LoanContract;
import com.isiweek.loan_contract.repos.LoanContractRepository;
import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.phone_notification.domain.PhoneNotification;
import com.isiweek.phone_notification.model.PhoneNotificationDTO;
import com.isiweek.phone_notification.repos.PhoneNotificationRepository;
import com.isiweek.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PhoneNotificationService {

    private final PhoneNotificationRepository phoneNotificationRepository;
    private final PersonRepository personRepository;
    private final LoanContractRepository loanContractRepository;

    public PhoneNotificationService(final PhoneNotificationRepository phoneNotificationRepository,
            final PersonRepository personRepository,
            final LoanContractRepository loanContractRepository) {
        this.phoneNotificationRepository = phoneNotificationRepository;
        this.personRepository = personRepository;
        this.loanContractRepository = loanContractRepository;
    }

    public List<PhoneNotificationDTO> findAll() {
        final List<PhoneNotification> phoneNotifications = phoneNotificationRepository.findAll(Sort.by("id"));
        return phoneNotifications.stream()
                .map(phoneNotification -> mapToDTO(phoneNotification, new PhoneNotificationDTO()))
                .toList();
    }

    public PhoneNotificationDTO get(final Long id) {
        return phoneNotificationRepository.findById(id)
                .map(phoneNotification -> mapToDTO(phoneNotification, new PhoneNotificationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PhoneNotificationDTO phoneNotificationDTO) {
        final PhoneNotification phoneNotification = new PhoneNotification();
        mapToEntity(phoneNotificationDTO, phoneNotification);
        return phoneNotificationRepository.save(phoneNotification).getId();
    }

    public void update(final Long id, final PhoneNotificationDTO phoneNotificationDTO) {
        final PhoneNotification phoneNotification = phoneNotificationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(phoneNotificationDTO, phoneNotification);
        phoneNotificationRepository.save(phoneNotification);
    }

    public void delete(final Long id) {
        phoneNotificationRepository.deleteById(id);
    }

    private PhoneNotificationDTO mapToDTO(final PhoneNotification phoneNotification,
            final PhoneNotificationDTO phoneNotificationDTO) {
        phoneNotificationDTO.setId(phoneNotification.getId());
        phoneNotificationDTO.setSubject(phoneNotification.getSubject());
        phoneNotificationDTO.setSentAt(phoneNotification.getSentAt());
        phoneNotificationDTO.setBody(phoneNotification.getBody());
        phoneNotificationDTO.setDateOfCall(phoneNotification.getDateOfCall());
        phoneNotificationDTO.setPerson(phoneNotification.getPerson() == null ? null : phoneNotification.getPerson().getId());
        phoneNotificationDTO.setLoanContract(phoneNotification.getLoanContract() == null ? null : phoneNotification.getLoanContract().getId());
        phoneNotificationDTO.setPerson(phoneNotification.getPerson() == null ? null : phoneNotification.getPerson().getId());
        phoneNotificationDTO.setLoanContract(phoneNotification.getLoanContract() == null ? null : phoneNotification.getLoanContract().getId());
        return phoneNotificationDTO;
    }

    private PhoneNotification mapToEntity(final PhoneNotificationDTO phoneNotificationDTO,
            final PhoneNotification phoneNotification) {
        phoneNotification.setSubject(phoneNotificationDTO.getSubject());
        phoneNotification.setSentAt(phoneNotificationDTO.getSentAt());
        phoneNotification.setBody(phoneNotificationDTO.getBody());
        phoneNotification.setDateOfCall(phoneNotificationDTO.getDateOfCall());
        final Person person = phoneNotificationDTO.getPerson() == null ? null : personRepository.findById(phoneNotificationDTO.getPerson())
                .orElseThrow(() -> new NotFoundException("person not found"));
        phoneNotification.setPerson(person);
        final LoanContract loanContract = phoneNotificationDTO.getLoanContract() == null ? null : loanContractRepository.findById(phoneNotificationDTO.getLoanContract())
                .orElseThrow(() -> new NotFoundException("loanContract not found"));
        phoneNotification.setLoanContract(loanContract);
        return phoneNotification;
    }

}
