package com.isiweekloan.dto;

import com.isiweekloan.annotation.CheckDate;
import com.isiweekloan.entity.LoanContractEntity;
import com.isiweekloan.entity.PaymentStatusEntity;
import com.isiweekloan.entity.PaymentTypeEntity;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.sql.Date;

@ApiModel()
public class PaymentDetailsDto extends AbstractDto<Date> {
    @CheckDate
    private Date paymentDate;
    private LoanContractEntity loanContract;
    private PaymentStatusEntity paymentStatus;
    private PaymentTypeEntity paymentType;
    private Long id;
    @NotNull
    private Long idLoanContract;
    @NotNull
    private BigDecimal paymentAmount;
    @NotNull
    private Long idPaymentType;
    @NotNull
    private Long idPaymentStatus;
    @Size(max = 1024)
    @NotBlank
    private String notes;

    public PaymentDetailsDto() {
    }

    public void setPaymentDate(java.sql.Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public java.util.Date getPaymentDate() {
        return this.paymentDate;
    }

    public void setLoanContract(LoanContractEntity loanContract) {
        this.loanContract = loanContract;
    }

    public LoanContractEntity getLoanContract() {
        return this.loanContract;
    }

    public void setPaymentStatus(PaymentStatusEntity paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentStatusEntity getPaymentStatus() {
        return this.paymentStatus;
    }

    public void setPaymentType(PaymentTypeEntity paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentTypeEntity getPaymentType() {
        return this.paymentType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setIdLoanContract(Long idLoanContract) {
        this.idLoanContract = idLoanContract;
    }

    public Long getIdLoanContract() {
        return this.idLoanContract;
    }

    public void setPaymentAmount(java.math.BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public java.math.BigDecimal getPaymentAmount() {
        return this.paymentAmount;
    }

    public void setIdPaymentType(Long idPaymentType) {
        this.idPaymentType = idPaymentType;
    }

    public Long getIdPaymentType() {
        return this.idPaymentType;
    }

    public void setIdPaymentStatus(Long idPaymentStatus) {
        this.idPaymentStatus = idPaymentStatus;
    }

    public Long getIdPaymentStatus() {
        return this.idPaymentStatus;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return this.notes;
    }
}