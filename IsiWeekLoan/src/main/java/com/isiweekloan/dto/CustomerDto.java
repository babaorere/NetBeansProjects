package com.isiweekloan.dto;

import com.isiweekloan.entity.LoanContractEntity;
import com.isiweekloan.entity.PersonEntity;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Collection;

@ApiModel()
public class CustomerDto extends AbstractDto<Long> {
    private Long creditScore;
    private PersonEntity person;
    private Collection<LoanContractEntity> loanContractEntityCollection;
    private Long id;
    @NotNull
    private Long idPerson;
    @NotNull
    private BigDecimal maxLoanAmount;
    @Size(max = 256)
    @NotBlank
    private String observations;

    public CustomerDto() {
    }

    public void setCreditScore(Long creditScore) {
        this.creditScore = creditScore;
    }

    public Long getCreditScore() {
        return this.creditScore;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    public PersonEntity getPerson() {
        return this.person;
    }

    public void setLoanContractEntityCollection(java.util.Collection<com.isiweekloan.entity.LoanContractEntity> loanContractEntityCollection) {
        this.loanContractEntityCollection = loanContractEntityCollection;
    }

    public java.util.Collection<com.isiweekloan.entity.LoanContractEntity> getLoanContractEntityCollection() {
        return this.loanContractEntityCollection;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public Long getIdPerson() {
        return this.idPerson;
    }

    public void setMaxLoanAmount(java.math.BigDecimal maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }

    public java.math.BigDecimal getMaxLoanAmount() {
        return this.maxLoanAmount;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getObservations() {
        return this.observations;
    }
}