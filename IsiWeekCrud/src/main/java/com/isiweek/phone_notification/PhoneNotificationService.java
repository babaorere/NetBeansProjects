package com.isiweek.phone_notification;

import com.isiweek.loan_contract.LoanContract;
import com.isiweek.loan_contract.LoanContractRepository;
import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PhoneNotificationService {

    private final PhoneNotificationRepository phoneNotificationRepository;
    private final LoanContractRepository loanContractRepository;
    private final PersonRepository personRepository;

    public PhoneNotificationService(final PhoneNotificationRepository phoneNotificationRepository,
            final LoanContractRepository loanContractRepository,
            final PersonRepository personRepository) {
        this.phoneNotificationRepository = phoneNotificationRepository;
        this.loanContractRepository = loanContractRepository;
        this.personRepository = personRepository;
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
        phoneNotificationDTO.setDateCreated(phoneNotification.getDateCreated());
        phoneNotificationDTO.setDateOfCall(phoneNotification.getDateOfCall());
        phoneNotificationDTO.setLastUpdated(phoneNotification.getLastUpdated());
        phoneNotificationDTO.setSentAt(phoneNotification.getSentAt());
        phoneNotificationDTO.setSubject(phoneNotification.getSubject());
        phoneNotificationDTO.setBody(phoneNotification.getBody());
        phoneNotificationDTO.setLoanContract(phoneNotification.getLoanContract() == null ? null : phoneNotification.getLoanContract().getId());
        phoneNotificationDTO.setPerson(phoneNotification.getPerson() == null ? null : phoneNotification.getPerson().getId());
        return phoneNotificationDTO;
    }

    private PhoneNotification mapToEntity(final PhoneNotificationDTO phoneNotificationDTO,
            final PhoneNotification phoneNotification) {
        phoneNotification.setDateCreated(phoneNotificationDTO.getDateCreated());
        phoneNotification.setDateOfCall(phoneNotificationDTO.getDateOfCall());
        phoneNotification.setLastUpdated(phoneNotificationDTO.getLastUpdated());
        phoneNotification.setSentAt(phoneNotificationDTO.getSentAt());
        phoneNotification.setSubject(phoneNotificationDTO.getSubject());
        phoneNotification.setBody(phoneNotificationDTO.getBody());
        final LoanContract loanContract = phoneNotificationDTO.getLoanContract() == null ? null : loanContractRepository.findById(phoneNotificationDTO.getLoanContract())
                .orElseThrow(() -> new NotFoundException("loanContract not found"));
        phoneNotification.setLoanContract(loanContract);
        final Person person = phoneNotificationDTO.getPerson() == null ? null : personRepository.findById(phoneNotificationDTO.getPerson())
                .orElseThrow(() -> new NotFoundException("person not found"));
        phoneNotification.setPerson(person);
        return phoneNotification;
    }

}
