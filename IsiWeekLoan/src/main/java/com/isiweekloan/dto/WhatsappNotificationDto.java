package com.isiweekloan.dto;

import com.isiweekloan.annotation.CheckDate;
import com.isiweekloan.entity.LoanContractEntity;
import com.isiweekloan.entity.PersonEntity;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;

@ApiModel()
public class WhatsappNotificationDto extends AbstractDto<Date> {
    @CheckDate
    private Date dateSent;
    private LoanContractEntity loanContract;
    private PersonEntity person;
    private Long id;
    @NotNull
    private Long idPerson;
    @NotNull
    private Long idLoanContract;
    @Size(max = 256)
    @NotBlank
    private String subject;
    @Size(max = 256)
    @NotBlank
    private String sentAt;
    @Size(max = 1024)
    @NotBlank
    private String body;

    public WhatsappNotificationDto() {
    }

    public void setDateSent(java.sql.Date dateSent) {
        this.dateSent = dateSent;
    }

    public java.util.Date getDateSent() {
        return this.dateSent;
    }

    public void setLoanContract(LoanContractEntity loanContract) {
        this.loanContract = loanContract;
    }

    public LoanContractEntity getLoanContract() {
        return this.loanContract;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    public PersonEntity getPerson() {
        return this.person;
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

    public void setIdLoanContract(Long idLoanContract) {
        this.idLoanContract = idLoanContract;
    }

    public Long getIdLoanContract() {
        return this.idLoanContract;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public String getSentAt() {
        return this.sentAt;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }
}