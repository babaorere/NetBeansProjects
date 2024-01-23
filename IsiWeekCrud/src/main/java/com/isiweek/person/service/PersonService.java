package com.isiweek.person.service;

import com.isiweek.company.Company;
import com.isiweek.company.CompanyRepository;
import com.isiweek.country.domain.Country;
import com.isiweek.country.repos.CountryRepository;
import com.isiweek.criminal_record.domain.CriminalRecord;
import com.isiweek.criminal_record.repos.CriminalRecordRepository;
import com.isiweek.customer.domain.Customer;
import com.isiweek.customer.repos.CustomerRepository;
import com.isiweek.doc_type.domain.DocType;
import com.isiweek.doc_type.repos.DocTypeRepository;
import com.isiweek.email_notification.domain.EmailNotification;
import com.isiweek.email_notification.repos.EmailNotificationRepository;
import com.isiweek.employee.domain.Employee;
import com.isiweek.employee.repos.EmployeeRepository;
import com.isiweek.loan_collector.domain.LoanCollector;
import com.isiweek.loan_collector.repos.LoanCollectorRepository;
import com.isiweek.marital_status.domain.MaritalStatus;
import com.isiweek.marital_status.repos.MaritalStatusRepository;
import com.isiweek.person.domain.Person;
import com.isiweek.person.model.PersonDTO;
import com.isiweek.person.repos.PersonRepository;
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
public class PersonService {

    private final PersonRepository personRepository;
    private final MaritalStatusRepository maritalStatusRepository;
    private final DocTypeRepository docTypeRepository;
    private final CountryRepository countryRepository;
    private final CompanyRepository companyRepository;
    private final CriminalRecordRepository criminalRecordRepository;
    private final CustomerRepository customerRepository;
    private final EmailNotificationRepository emailNotificationRepository;
    private final EmployeeRepository employeeRepository;
    private final LoanCollectorRepository loanCollectorRepository;
    private final PhoneNotificationRepository phoneNotificationRepository;
    private final WhatsappNotificationRepository whatsappNotificationRepository;

    public PersonService(final PersonRepository personRepository,
        final MaritalStatusRepository maritalStatusRepository,
        final DocTypeRepository docTypeRepository, final CountryRepository countryRepository,
        final CompanyRepository companyRepository,
        final CriminalRecordRepository criminalRecordRepository,
        final CustomerRepository customerRepository,
        final EmailNotificationRepository emailNotificationRepository,
        final EmployeeRepository employeeRepository,
        final LoanCollectorRepository loanCollectorRepository,
        final PhoneNotificationRepository phoneNotificationRepository,
        final WhatsappNotificationRepository whatsappNotificationRepository) {
        this.personRepository = personRepository;
        this.maritalStatusRepository = maritalStatusRepository;
        this.docTypeRepository = docTypeRepository;
        this.countryRepository = countryRepository;
        this.companyRepository = companyRepository;
        this.criminalRecordRepository = criminalRecordRepository;
        this.customerRepository = customerRepository;
        this.emailNotificationRepository = emailNotificationRepository;
        this.employeeRepository = employeeRepository;
        this.loanCollectorRepository = loanCollectorRepository;
        this.phoneNotificationRepository = phoneNotificationRepository;
        this.whatsappNotificationRepository = whatsappNotificationRepository;
    }

    public List<PersonDTO> findAll() {
        final List<Person> persons = personRepository.findAll(Sort.by("id"));
        return persons.stream()
            .map(person -> Person.mapToDTO(person, new PersonDTO()))
            .toList();
    }

    public PersonDTO get(final Long id) {
        return personRepository.findById(id)
            .map(person -> Person.mapToDTO(person, new PersonDTO()))
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

    private Person mapToEntity(final PersonDTO personDTO, final Person person) {
        person.setIdDoc(personDTO.getIdDoc());
        person.setEmail(personDTO.getEmail());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setPhoneNumber1(personDTO.getPhoneNumber1());
        person.setPhoneNumber2(personDTO.getPhoneNumber2());
        person.setDateOfBirth(personDTO.getDateOfBirth());
        person.setGender(personDTO.getGender());
        person.setAddress(personDTO.getAddress());
        person.setCity(personDTO.getCity());
        person.setState(personDTO.getState());
        person.setObservations(personDTO.getObservations());
        person.setOccupation(personDTO.getOccupation());
        person.setPhoto(personDTO.getPhoto());

        final MaritalStatus maritalStatus = personDTO.getMaritalStatus() == null ? null : maritalStatusRepository.findById(personDTO.getMaritalStatus())
            .orElseThrow(() -> new NotFoundException("maritalStatus not found"));
        person.setMaritalStatus(maritalStatus);
        final DocType docType = personDTO.getDocType() == null ? null : docTypeRepository.findById(personDTO.getDocType())
            .orElseThrow(() -> new NotFoundException("docType not found"));
        person.setDocType(docType);
        final Country country = personDTO.getCountry() == null ? null : countryRepository.findById(personDTO.getCountry())
            .orElseThrow(() -> new NotFoundException("country not found"));
        person.setCountry(country);
        final Company company = personDTO.getCompany() == null ? null : companyRepository.findById(personDTO.getCompany())
            .orElseThrow(() -> new NotFoundException("company not found"));
        person.addCompany(company);
        final CriminalRecord criminalRecord = personDTO.getCriminalRecord() == null ? null : criminalRecordRepository.findById(personDTO.getCriminalRecord())
            .orElseThrow(() -> new NotFoundException("criminalRecord not found"));
        person.setCriminalRecord(criminalRecord);

        return person;
    }

    public boolean idDocExists(final String inIdDoc) {
        return personRepository.existsByIdDocIgnoreCase(inIdDoc);
    }

    public boolean emailExists(final String email) {
        return personRepository.existsByEmailIgnoreCase(email);
    }

    public String getReferencedWarning(final Long id) {
        final Person person = personRepository.findById(id)
            .orElseThrow(NotFoundException::new);
        final Customer personCustomer = customerRepository.findFirstByPerson(person);
        if (personCustomer != null) {
            return WebUtils.getMessage("person.customer.person.referenced", personCustomer.getId());
        }

        final EmailNotification personEmailNotification = emailNotificationRepository.findFirstByPerson(person);
        if (personEmailNotification != null) {
            return WebUtils.getMessage("person.emailNotification.person.referenced", personEmailNotification.getId());
        }

        final Employee personEmployee = employeeRepository.findFirstByPerson(person);
        if (personEmployee != null) {
            return WebUtils.getMessage("person.employee.person.referenced", personEmployee.getId());
        }
        final Employee managerEmployee = employeeRepository.findFirstByManager(person);
        if (managerEmployee != null) {
            return WebUtils.getMessage("person.employee.manager.referenced", managerEmployee.getId());
        }

        final LoanCollector personLoanCollector = loanCollectorRepository.findFirstByPerson(person);
        if (personLoanCollector != null) {
            return WebUtils.getMessage("person.loanCollector.person.referenced", personLoanCollector.getId());
        }

        final PhoneNotification personPhoneNotification = phoneNotificationRepository.findFirstByPerson(person);
        if (personPhoneNotification != null) {
            return WebUtils.getMessage("person.phoneNotification.person.referenced", personPhoneNotification.getId());
        }

        final WhatsappNotification personWhatsappNotification = whatsappNotificationRepository.findFirstByPerson(person);
        if (personWhatsappNotification != null) {
            return WebUtils.getMessage("person.whatsappNotification.person.referenced", personWhatsappNotification.getId());
        }

        return null;
    }

    public void addPersonToCompany(Long personId, Long companyId) {
        Person person = personRepository.findById(personId).orElse(null);
        Company company = companyRepository.findById(companyId).orElse(null);

        if (person != null && company != null) {
            company.addPerson(person);
            companyRepository.save(company); // Guarda la empresa con la nueva relación
        }
    }

    public void addCompanyToPerson(Long companyId, Long personId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        Person person = personRepository.findById(personId).orElse(null);

        if (company != null && person != null) {
            person.addCompany(company);
            personRepository.save(person); // Guarda la persona con la nueva relación
        }
    }

}
