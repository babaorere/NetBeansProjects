package com.isiweek.person;

import com.isiweek.country.Country;
import com.isiweek.criminal_record.CriminalRecord;
import com.isiweek.doc_type.DocType;
import com.isiweek.email_notification.EmailNotification;
import com.isiweek.employee.Employee;
import com.isiweek.loan_collector.LoanCollector;
import com.isiweek.marital_status.MaritalStatus;
import com.isiweek.phone_notification.PhoneNotification;
import com.isiweek.user.User;
import com.isiweek.whatsapp_notification.WhatsappNotification;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Person {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private Boolean gender;

    @Column(nullable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @Column(nullable = false, unique = true, length = 16)
    private String idDoc;

    @Column(nullable = false, length = 16)
    private String phoneNumber1;

    @Column(length = 16)
    private String phoneNumber2;

    @Column(nullable = false, unique = true, length = 32)
    private String email;

    @Column(nullable = false, length = 64)
    private String city;

    @Column(nullable = false, length = 64)
    private String firstName;

    @Column(nullable = false, length = 64)
    private String lastName;

    @Column(nullable = false, length = 64)
    private String state;

    @Column(nullable = false, length = 256)
    private String address;

    @Column(length = 256)
    private String observations;

    @Column(length = 256)
    private String occupation;

    @Column
    private String photo;

    @OneToMany(mappedBy = "person")
    private Set<EmailNotification> personEmailNotifications;

    @OneToMany(mappedBy = "manager")
    private Set<Employee> managerEmployees;

    @OneToMany(mappedBy = "person")
    private Set<Employee> personEmployees;

    @OneToMany(mappedBy = "person")
    private Set<LoanCollector> personLoanCollectors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_type_id", nullable = false)
    private DocType docType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criminal_record_id", nullable = false)
    private CriminalRecord criminalRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marital_status_id", nullable = false)
    private MaritalStatus maritalStatus;

    @OneToMany(mappedBy = "person")
    private Set<PhoneNotification> personPhoneNotifications;

    @OneToMany(mappedBy = "person")
    private Set<WhatsappNotification> personWhatsappNotifications;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
