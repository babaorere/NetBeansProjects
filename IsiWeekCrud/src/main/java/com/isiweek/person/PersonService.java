package com.isiweek.person;

import com.isiweek.company.Company;
import com.isiweek.company.CompanyRepository;
import com.isiweek.country.CountryRepository;
import com.isiweek.criminal_record.CriminalRecordRepository;
import com.isiweek.customer.Customer;
import com.isiweek.customer.CustomerRepository;
import com.isiweek.doc_type.DocTypeRepository;
import com.isiweek.email_notification.EmailNotification;
import com.isiweek.email_notification.EmailNotificationRepository;
import com.isiweek.employee.Employee;
import com.isiweek.employee.EmployeeRepository;
import com.isiweek.loan_collector.domain.LoanCollector;
import com.isiweek.loan_collector.repos.LoanCollectorRepository;
import com.isiweek.marital_status.MaritalStatusRepository;
import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
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

    public List<Person> findAll() {
        return personRepository.findAll(Sort.by("id"));
    }

    public Person get(final Long id) {
        return personRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Long create(final Person inPerson) {
        return personRepository.save(inPerson).getId();
    }

    public void update(final Long id, final Person inPerson) {
        personRepository.save(inPerson);
    }

    public void delete(final Long id) {
        personRepository.deleteById(id);
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
