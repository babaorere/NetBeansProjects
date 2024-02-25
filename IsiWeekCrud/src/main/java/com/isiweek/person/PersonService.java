package com.isiweek.person;

import com.isiweek.country.Country;
import com.isiweek.country.CountryRepository;
import com.isiweek.criminal_record.CriminalRecord;
import com.isiweek.criminal_record.CriminalRecordRepository;
import com.isiweek.doc_type.DocType;
import com.isiweek.doc_type.DocTypeRepository;
import com.isiweek.email_notification.EmailNotification;
import com.isiweek.email_notification.EmailNotificationRepository;
import com.isiweek.employee.Employee;
import com.isiweek.employee.EmployeeRepository;
import com.isiweek.lender.Lender;
import com.isiweek.lender.LenderRepository;
import com.isiweek.loan_collector.LoanCollector;
import com.isiweek.loan_collector.LoanCollectorRepository;
import com.isiweek.marital_status.MaritalStatus;
import com.isiweek.marital_status.MaritalStatusRepository;
import com.isiweek.phone_notification.PhoneNotification;
import com.isiweek.phone_notification.PhoneNotificationRepository;
import com.isiweek.user.User;
import com.isiweek.user.UserRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import com.isiweek.whatsapp_notification.WhatsappNotification;
import com.isiweek.whatsapp_notification.WhatsappNotificationRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final DocTypeRepository docTypeRepository;
    private final CriminalRecordRepository criminalRecordRepository;
    private final CountryRepository countryRepository;
    private final MaritalStatusRepository maritalStatusRepository;
    private final UserRepository userRepository;
    private final LenderRepository lenderRepository;
    private final EmailNotificationRepository emailNotificationRepository;
    private final EmployeeRepository employeeRepository;
    private final LoanCollectorRepository loanCollectorRepository;
    private final PhoneNotificationRepository phoneNotificationRepository;
    private final WhatsappNotificationRepository whatsappNotificationRepository;

    public PersonService(final PersonRepository personRepository,
            final DocTypeRepository docTypeRepository,
            final CriminalRecordRepository criminalRecordRepository,
            final CountryRepository countryRepository,
            final MaritalStatusRepository maritalStatusRepository,
            final UserRepository userRepository, final LenderRepository lenderRepository,
            final EmailNotificationRepository emailNotificationRepository,
            final EmployeeRepository employeeRepository,
            final LoanCollectorRepository loanCollectorRepository,
            final PhoneNotificationRepository phoneNotificationRepository,
            final WhatsappNotificationRepository whatsappNotificationRepository) {
        this.personRepository = personRepository;
        this.docTypeRepository = docTypeRepository;
        this.criminalRecordRepository = criminalRecordRepository;
        this.countryRepository = countryRepository;
        this.maritalStatusRepository = maritalStatusRepository;
        this.userRepository = userRepository;
        this.lenderRepository = lenderRepository;
        this.emailNotificationRepository = emailNotificationRepository;
        this.employeeRepository = employeeRepository;
        this.loanCollectorRepository = loanCollectorRepository;
        this.phoneNotificationRepository = phoneNotificationRepository;
        this.whatsappNotificationRepository = whatsappNotificationRepository;
    }

    public List<PersonDTO> findAll() {
        final List<Person> persons = personRepository.findAll(Sort.by("id"));
        return persons.stream()
                .map(person -> mapToDTO(person, new PersonDTO()))
                .toList();
    }

    public PersonDTO get(final Long id) {
        return personRepository.findById(id)
                .map(person -> mapToDTO(person, new PersonDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PersonDTO personDTO) {
        final Person person = new Person();
        mapToEntity(personDTO, person);
        return personRepository.save(person).getId();
    }

    public void update(final Long id, final PersonDTO personDTO) {
        final Person person = personRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(personDTO, person);
        personRepository.save(person);
    }

    public void delete(final Long id) {
        personRepository.deleteById(id);
    }

    private PersonDTO mapToDTO(final Person person, final PersonDTO personDTO) {
        personDTO.setId(person.getId());
        personDTO.setDateOfBirth(person.getDateOfBirth());
        personDTO.setGender(person.getGender());
        personDTO.setDateCreated(person.getDateCreated());
        personDTO.setLastUpdated(person.getLastUpdated());
        personDTO.setIdDoc(person.getIdDoc());
        personDTO.setPhoneNumber1(person.getPhoneNumber1());
        personDTO.setPhoneNumber2(person.getPhoneNumber2());
        personDTO.setEmail(person.getEmail());
        personDTO.setCity(person.getCity());
        personDTO.setFirstName(person.getFirstName());
        personDTO.setLastName(person.getLastName());
        personDTO.setState(person.getState());
        personDTO.setAddress(person.getAddress());
        personDTO.setObservations(person.getObservations());
        personDTO.setOccupation(person.getOccupation());
        personDTO.setPhoto(person.getPhoto());
        personDTO.setDocType(person.getDocType() == null ? null : person.getDocType().getId());
        personDTO.setCriminalRecord(person.getCriminalRecord() == null ? null : person.getCriminalRecord().getId());
        personDTO.setCountry(person.getCountry() == null ? null : person.getCountry().getId());
        personDTO.setMaritalStatus(person.getMaritalStatus() == null ? null : person.getMaritalStatus().getId());
        personDTO.setUser(person.getUser() == null ? null : person.getUser().getId());
        return personDTO;
    }

    private Person mapToEntity(final PersonDTO personDTO, final Person person) {
        person.setDateOfBirth(personDTO.getDateOfBirth());
        person.setGender(personDTO.getGender());
        person.setDateCreated(personDTO.getDateCreated());
        person.setLastUpdated(personDTO.getLastUpdated());
        person.setIdDoc(personDTO.getIdDoc());
        person.setPhoneNumber1(personDTO.getPhoneNumber1());
        person.setPhoneNumber2(personDTO.getPhoneNumber2());
        person.setEmail(personDTO.getEmail());
        person.setCity(personDTO.getCity());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setState(personDTO.getState());
        person.setAddress(personDTO.getAddress());
        person.setObservations(personDTO.getObservations());
        person.setOccupation(personDTO.getOccupation());
        person.setPhoto(personDTO.getPhoto());
        final DocType docType = personDTO.getDocType() == null ? null : docTypeRepository.findById(personDTO.getDocType())
                .orElseThrow(() -> new NotFoundException("docType not found"));
        person.setDocType(docType);
        final CriminalRecord criminalRecord = personDTO.getCriminalRecord() == null ? null : criminalRecordRepository.findById(personDTO.getCriminalRecord())
                .orElseThrow(() -> new NotFoundException("criminalRecord not found"));
        person.setCriminalRecord(criminalRecord);
        final Country country = personDTO.getCountry() == null ? null : countryRepository.findById(personDTO.getCountry())
                .orElseThrow(() -> new NotFoundException("country not found"));
        person.setCountry(country);
        final MaritalStatus maritalStatus = personDTO.getMaritalStatus() == null ? null : maritalStatusRepository.findById(personDTO.getMaritalStatus())
                .orElseThrow(() -> new NotFoundException("maritalStatus not found"));
        person.setMaritalStatus(maritalStatus);
        final User user = personDTO.getUser() == null ? null : userRepository.findById(personDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        person.setUser(user);
        return person;
    }

    public boolean idDocExists(final String idDoc) {
        return personRepository.existsByIdDocIgnoreCase(idDoc);
    }

    public boolean emailExists(final String email) {
        return personRepository.existsByEmailIgnoreCase(email);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Person person = personRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Lender personLender = lenderRepository.findFirstByPerson(person);
        if (personLender != null) {
            referencedWarning.setKey("person.lender.person.referenced");
            referencedWarning.addParam(personLender.getId());
            return referencedWarning;
        }
        final EmailNotification personEmailNotification = emailNotificationRepository.findFirstByPerson(person);
        if (personEmailNotification != null) {
            referencedWarning.setKey("person.emailNotification.person.referenced");
            referencedWarning.addParam(personEmailNotification.getId());
            return referencedWarning;
        }
        final Employee managerEmployee = employeeRepository.findFirstByManager(person);
        if (managerEmployee != null) {
            referencedWarning.setKey("person.employee.manager.referenced");
            referencedWarning.addParam(managerEmployee.getId());
            return referencedWarning;
        }
        final Employee personEmployee = employeeRepository.findFirstByPerson(person);
        if (personEmployee != null) {
            referencedWarning.setKey("person.employee.person.referenced");
            referencedWarning.addParam(personEmployee.getId());
            return referencedWarning;
        }
        final LoanCollector personLoanCollector = loanCollectorRepository.findFirstByPerson(person);
        if (personLoanCollector != null) {
            referencedWarning.setKey("person.loanCollector.person.referenced");
            referencedWarning.addParam(personLoanCollector.getId());
            return referencedWarning;
        }
        final PhoneNotification personPhoneNotification = phoneNotificationRepository.findFirstByPerson(person);
        if (personPhoneNotification != null) {
            referencedWarning.setKey("person.phoneNotification.person.referenced");
            referencedWarning.addParam(personPhoneNotification.getId());
            return referencedWarning;
        }
        final WhatsappNotification personWhatsappNotification = whatsappNotificationRepository.findFirstByPerson(person);
        if (personWhatsappNotification != null) {
            referencedWarning.setKey("person.whatsappNotification.person.referenced");
            referencedWarning.addParam(personWhatsappNotification.getId());
            return referencedWarning;
        }
        return null;
    }

}
