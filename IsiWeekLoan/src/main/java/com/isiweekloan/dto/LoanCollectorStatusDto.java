package com.isiweekloan.dto;

import com.isiweekloan.entity.LoanCollectorEntity;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collection;

@ApiModel()
public class LoanCollectorStatusDto extends AbstractDto<Collection<LoanCollectorEntity>> {
    private Collection<LoanCollectorEntity> loanCollectorEntityCollection;
    private Long id;
    @Size(max = 128)
    @NotBlank
    private String name;
    @Size(max = 512)
    @NotBlank
    private String description;

    public LoanCollectorStatusDto() {
    }

    public void setLoanCollectorEntityCollection(java.util.Collection<com.isiweekloan.entity.LoanCollectorEntity> loanCollectorEntityCollection) {
        this.loanCollectorEntityCollection = loanCollectorEntityCollection;
    }

    public java.util.Collection<com.isiweekloan.entity.LoanCollectorEntity> getLoanCollectorEntityCollection() {
        return this.loanCollectorEntityCollection;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}