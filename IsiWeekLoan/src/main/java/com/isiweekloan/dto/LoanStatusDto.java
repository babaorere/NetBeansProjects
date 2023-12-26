package com.isiweekloan.dto;

import com.isiweekloan.entity.LoanContractEntity;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collection;

@ApiModel()
public class LoanStatusDto extends AbstractDto<Collection<LoanContractEntity>> {
    private Collection<LoanContractEntity> loanContractEntityCollection;
    private Long id;
    @Size(max = 128)
    @NotBlank
    private String name;
    @Size(max = 512)
    @NotBlank
    private String description;

    public LoanStatusDto() {
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