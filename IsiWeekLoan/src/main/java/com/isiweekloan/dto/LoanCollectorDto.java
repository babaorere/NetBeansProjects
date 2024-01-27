package com.isiweekloan.dto;

import com.isiweekloan.entity.LoanCollectorStatusEntity;
import com.isiweekloan.entity.PersonEntity;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

@ApiModel()
public class LoanCollectorDto extends AbstractDto<PersonEntity> {
    private PersonEntity person;
    private long idPerson;
    private LoanCollectorStatusEntity lcStatus;
    private Long id;
    @NotNull
    private Long idLcStatus;

    public LoanCollectorDto() {
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    public PersonEntity getPerson() {
        return this.person;
    }

    public void setIdPerson(long idPerson) {
        this.idPerson = idPerson;
    }

    public long getIdPerson() {
        return this.idPerson;
    }

    public void setLcStatus(LoanCollectorStatusEntity lcStatus) {
        this.lcStatus = lcStatus;
    }

    public LoanCollectorStatusEntity getLcStatus() {
        return this.lcStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setIdLcStatus(Long idLcStatus) {
        this.idLcStatus = idLcStatus;
    }

    public Long getIdLcStatus() {
        return this.idLcStatus;
    }
}