package com.isiweekloan.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@ApiModel()
public class CompanyDto extends AbstractDto<Collection<PersonEntity>> {
    private Collection<PersonEntity> personEntityCollection;
    private Long id;
    @Size(max = 128)
    @NotBlank
    private String name;
    @Size(max = 32)
    @NotBlank
    private String taxidnumber;
    @Size(max = 512)
    @NotBlank
    private String description;
    @Size(max = 256)
    @NotBlank
    private String address;
    @Size(max = 20)
    @NotBlank
    private String phone1;
    @Size(max = 20)
    private String phone2;
    @CheckEmail
    @Size(max = 32)
    @NotBlank
    private String email;
    @Size(max = 128)
    @NotBlank
    private String primaryContact;

    public CompanyDto() {
    }

    public void setPersonEntityCollection(java.util.Collection<com.isiweekloan.entity.PersonEntity> personEntityCollection) {
        this.personEntityCollection = personEntityCollection;
    }

    public java.util.Collection<com.isiweekloan.entity.PersonEntity> getPersonEntityCollection() {
        return this.personEntityCollection;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setTaxidnumber(String taxidnumber) {
        this.taxidnumber = taxidnumber;
    }

    public String getTaxidnumber() {
        return this.taxidnumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone1() {
        return this.phone1;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone2() {
        return this.phone2;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPrimaryContact(String primaryContact) {
        this.primaryContact = primaryContact;
    }

    public String getPrimaryContact() {
        return this.primaryContact;
    }
}