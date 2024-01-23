package com.isiweek.person.domain;

import com.isiweek.company.Company;
import com.isiweek.country.domain.Country;
import com.isiweek.criminal_record.domain.CriminalRecord;
import com.isiweek.customer.domain.Customer;
import com.isiweek.doc_type.domain.DocType;
import com.isiweek.email_notification.domain.EmailNotification;
import com.isiweek.employee.domain.Employee;
import com.isiweek.loan_collector.domain.LoanCollector;
import com.isiweek.marital_status.domain.MaritalStatus;
import com.isiweek.person.model.PersonDTO;
import com.isiweek.phone_notification.domain.PhoneNotification;
import com.isiweek.whatsapp_notification.domain.WhatsappNotification;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, unique = true, length = 16)
    private String idDoc;

    @NonNull
    @Column(nullable = false, unique = true, length = 32)
    private String email;

    @NonNull
    @Column(nullable = false, length = 64)
    private String firstName;

    @NonNull
    @Column(nullable = false, length = 64)
    private String lastName;

    @NonNull
    @Column(nullable = false, length = 16)
    private String phoneNumber1;

    @Column(nullable = true, length = 16)
    private String phoneNumber2;

    @NonNull
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @NonNull
    @Column(nullable = false)
    private Boolean gender;

    @NonNull
    @Column(nullable = false, length = 256)
    private String address;

    @NonNull
    @Column(nullable = false, length = 64)
    private String city;

    @NonNull
    @Column(nullable = false, length = 64)
    private String state;

    @Column(nullable = true, length = 256)
    private String observations;

    @Column(nullable = true, length = 256)
    private String occupation;

    @Column(nullable = true)
    private String photo;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<Customer> personCustomers;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<EmailNotification> personEmailNotifications;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<Employee> personEmployees;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private Set<Employee> managerEmployees;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<LoanCollector> personLoanCollectors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marital_status_id", nullable = false)
    private MaritalStatus maritalStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_type_id", nullable = false)
    private DocType docType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "company_person", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "company_id"))
    private Set<Company> companies = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criminal_record_id", nullable = false)
    private CriminalRecord criminalRecord;

    @OneToMany(mappedBy = "person")
    private Set<PhoneNotification> personPhoneNotifications;

    @OneToMany(mappedBy = "person")
    private Set<WhatsappNotification> personWhatsappNotifications;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    public Person(PersonDTO indto) {
        // Utiliza el mapper para mapear desde Person a PersonDTO
        Person entity = PersonMapper.INSTANCE.mapToEntity(indto);

        // Copia los valores mapeados a la instancia actual
        BeanUtils.copyProperties(entity, this);
    }

    @Override
    public String toString() {
        return "[\"id\": " + id + ", \"name\": " + firstName + " " + lastName + "]";
    }

    public void addCompany(Company inCompany) {
        companies.add(inCompany);
        inCompany.getPersons().add(this);
    }

}
