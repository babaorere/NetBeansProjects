package com.isiweekloan.dto;

import com.isiweekloan.annotation.CheckDate;
import com.isiweekloan.annotation.CheckEmail;
import com.isiweekloan.entity.*;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@ApiModel()
public class PersonDto extends AbstractDto<Date> {
    @CheckDate
    private Date dateOfBirth;
    private Character gender;
    @Size(max = 255)
    private String occupation;
    @Size(max = 255)
    private String photo;
    private List<LoanCollectorEntity> loanCollectorEntityCollection;
    private Collection<PhoneNotificationEntity> phoneNotificationEntityCollection;
    private Collection<EmployeeEntity> employeeEntityCollection;
    private Collection<EmployeeEntity> employeeEntityCollection1;
    private Collection<WhatsappNotificationEntity> whatsappNotificationEntityCollection;
    private CountryEntity country;
    private CompanyEntity company;
    private CriminalRecordEntity criminalRecord;
    private DocTypeEntity docType;
    private MaritalStatusEntity maritalStatus;
    @CheckEmail
    private Collection<EmailNotificationEntity> emailNotificationEntityCollection;
    private Collection<CustomerEntity> customerEntityCollection;
    private Long id;
    @Size(max = 16)
    @NotBlank
    private String identificationDocument;
    @NotNull
    private Long idDocType;
    @NotNull
    private Long idCountry;
    @NotNull
    private Long idCriminalRecord;
    @NotNull
    private Long idMaritalStatus;
    @CheckEmail
    @Size(max = 32)
    @NotBlank
    private String email;
    @Size(max = 64)
    @NotBlank
    private String firstName;
    @Size(max = 64)
    @NotBlank
    private String lastName;
    @Size(max = 16)
    @NotBlank
    private String phoneNumber1;
    @Size(max = 16)
    @NotBlank
    private String phoneNumber2;
    @Size(max = 256)
    @NotBlank
    private String address;
    @Size(max = 64)
    @NotBlank
    private String city;
    @Size(max = 64)
    @NotBlank
    private String state;
    @Size(max = 256)
    @NotBlank
    private String observations;

    public PersonDto() {
    }

    public void setDateOfBirth(java.sql.Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public java.sql.Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Character getGender() {
        return this.gender;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setLoanCollectorEntityCollection(java.util.List<LoanCollectorEntity> loanCollectorEntityCollection) {
        this.loanCollectorEntityCollection = loanCollectorEntityCollection;
    }

    public java.util.List<LoanCollectorEntity> getLoanCollectorEntityCollection() {
        return this.loanCollectorEntityCollection;
    }

    public void setPhoneNotificationEntityCollection(java.util.Collection<PhoneNotificationEntity> phoneNotificationEntityCollection) {
        this.phoneNotificationEntityCollection = phoneNotificationEntityCollection;
    }

    public java.util.Collection<PhoneNotificationEntity> getPhoneNotificationEntityCollection() {
        return this.phoneNotificationEntityCollection;
    }

    public void setEmployeeEntityCollection(java.util.Collection<EmployeeEntity> employeeEntityCollection) {
        this.employeeEntityCollection = employeeEntityCollection;
    }

    public java.util.Collection<EmployeeEntity> getEmployeeEntityCollection() {
        return this.employeeEntityCollection;
    }

    public void setEmployeeEntityCollection1(java.util.Collection<EmployeeEntity> employeeEntityCollection1) {
        this.employeeEntityCollection1 = employeeEntityCollection1;
    }

    public java.util.Collection<EmployeeEntity> getEmployeeEntityCollection1() {
        return this.employeeEntityCollection1;
    }

    public void setWhatsappNotificationEntityCollection(java.util.Collection<WhatsappNotificationEntity> whatsappNotificationEntityCollection) {
        this.whatsappNotificationEntityCollection = whatsappNotificationEntityCollection;
    }

    public java.util.Collection<WhatsappNotificationEntity> getWhatsappNotificationEntityCollection() {
        return this.whatsappNotificationEntityCollection;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public CountryEntity getCountry() {
        return this.country;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public CompanyEntity getCompany() {
        return this.company;
    }

    public void setCriminalRecord(CriminalRecordEntity criminalRecord) {
        this.criminalRecord = criminalRecord;
    }

    public CriminalRecordEntity getCriminalRecord() {
        return this.criminalRecord;
    }

    public void setDocType(DocTypeEntity docType) {
        this.docType = docType;
    }

    public DocTypeEntity getDocType() {
        return this.docType;
    }

    public void setMaritalStatus(MaritalStatusEntity maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public MaritalStatusEntity getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setEmailNotificationEntityCollection(java.util.Collection<EmailNotificationEntity> emailNotificationEntityCollection) {
        this.emailNotificationEntityCollection = emailNotificationEntityCollection;
    }

    public java.util.Collection<EmailNotificationEntity> getEmailNotificationEntityCollection() {
        return this.emailNotificationEntityCollection;
    }

    public void setCustomerEntityCollection(java.util.Collection<CustomerEntity> customerEntityCollection) {
        this.customerEntityCollection = customerEntityCollection;
    }

    public java.util.Collection<CustomerEntity> getCustomerEntityCollection() {
        return this.customerEntityCollection;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setIdentificationDocument(String identificationDocument) {
        this.identificationDocument = identificationDocument;
    }

    public String getIdentificationDocument() {
        return this.identificationDocument;
    }

    public void setIdDocType(Long idDocType) {
        this.idDocType = idDocType;
    }

    public Long getIdDocType() {
        return this.idDocType;
    }

    public void setIdCountry(Long idCountry) {
        this.idCountry = idCountry;
    }

    public Long getIdCountry() {
        return this.idCountry;
    }

    public void setIdCriminalRecord(Long idCriminalRecord) {
        this.idCriminalRecord = idCriminalRecord;
    }

    public Long getIdCriminalRecord() {
        return this.idCriminalRecord;
    }

    public void setIdMaritalStatus(Long idMaritalStatus) {
        this.idMaritalStatus = idMaritalStatus;
    }

    public Long getIdMaritalStatus() {
        return this.idMaritalStatus;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber1() {
        return this.phoneNumber1;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getPhoneNumber2() {
        return this.phoneNumber2;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getObservations() {
        return this.observations;
    }
}