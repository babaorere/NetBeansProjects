package com.isiweekloan.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person", schema = "isiweekloanservices", catalog = "")
public class PersonEntity {

    @Basic(optional = false)
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private java.sql.Date dateOfBirth;

    @Basic(optional = false)
    @Column(name = "gender")
    private Character gender;

    @Basic
    @Column(name = "occupation", nullable = true)
    private String occupation; // Store the person's occupation.

    @Basic
    @Column(name = "photo", nullable = true)
    private String photo; // Include a profile picture field if needed.    

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.LAZY)
    private List<LoanCollectorEntity> loanCollectorEntityCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.LAZY)
    private Collection<PhoneNotificationEntity> phoneNotificationEntityCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.LAZY)
    private Collection<EmployeeEntity> employeeEntityCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manager", fetch = FetchType.LAZY)
    private Collection<EmployeeEntity> employeeEntityCollection1;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.LAZY)
    private Collection<WhatsappNotificationEntity> whatsappNotificationEntityCollection;

    @JoinColumn(name = "country", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CountryEntity country;

    @JoinColumn(name = "company", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CompanyEntity company;

    @JoinColumn(name = "criminal_record", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CriminalRecordEntity criminalRecord;

    @JoinColumn(name = "doc_type", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DocTypeEntity docType;

    @JoinColumn(name = "marital_status", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MaritalStatusEntity maritalStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerson", fetch = FetchType.LAZY)
    private Collection<EmailNotificationEntity> emailNotificationEntityCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerson", fetch = FetchType.LAZY)
    private Collection<CustomerEntity> customerEntityCollection;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "identification_document", nullable = false, length = 16)
    private String identificationDocument;

    @Basic
    @Column(name = "id_doc_type", nullable = false)
    private Long idDocType;

    @Basic
    @Column(name = "id_country", nullable = false)
    private Long idCountry;

    @Basic
    @Column(name = "id_criminal_record", nullable = false)
    private Long idCriminalRecord;

    @Basic
    @Column(name = "id_marital_status", nullable = false)
    private Long idMaritalStatus;

    @Basic
    @Column(name = "email", nullable = false, length = 32)
    private String email;

    @Basic
    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;

    @Basic
    @Column(name = "phone_number1", nullable = false, length = 16)
    private String phoneNumber1;

    @Basic
    @Column(name = "phone_number2", nullable = false, length = 16)
    private String phoneNumber2;

    @Basic
    @Column(name = "address", nullable = false, length = 256)
    private String address;

    @Basic
    @Column(name = "city", nullable = false, length = 64)
    private String city;

    @Basic
    @Column(name = "state", nullable = false, length = 64)
    private String state;

    @Basic
    @Column(name = "observations", nullable = false, length = 256)
    private String observations;

    @Override
    public int hashCode() {
        return Objects.hash(id, identificationDocument, idDocType, idCountry, idCriminalRecord, idMaritalStatus, email, firstName, lastName, phoneNumber1, phoneNumber2, dateOfBirth, gender, address, city, state, observations);
    }

    @Override
    public boolean equals(final Object inO) {
        if (this == inO) {
            return true;
        }
        if (inO == null || getClass() != inO.getClass()) {
            return false;
        }
        PersonEntity that = (PersonEntity) inO;
        return Objects.equals(id, that.id) && Objects.equals(identificationDocument, that.identificationDocument) && Objects.equals(idDocType, that.idDocType) && Objects.equals(idCountry, that.idCountry) && Objects.equals(idCriminalRecord, that.idCriminalRecord) && Objects.equals(idMaritalStatus, that.idMaritalStatus) && Objects.equals(email, that.email) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(phoneNumber1, that.phoneNumber1) && Objects.equals(phoneNumber2, that.phoneNumber2) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(gender, that.gender) && Objects.equals(address, that.address) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(observations, that.observations);
    }

}
