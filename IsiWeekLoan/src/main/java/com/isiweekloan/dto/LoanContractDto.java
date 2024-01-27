package com.isiweekloan.dto;

import com.isiweekloan.annotation.CheckDate;
import com.isiweekloan.annotation.CheckEmail;
import com.isiweekloan.entity.*;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

@ApiModel()
public class LoanContractDto extends AbstractDto<Long> {
    private long loanTerm;
    @CheckDate
    private Date date;
    @CheckDate
    private Date dateOfMaturity;
    private long loanPurpose;
    private Collection<PhoneNotificationEntity> phoneNotificationEntityCollection;
    private Collection<WhatsappNotificationEntity> whatsappNotificationEntityCollection;
    private Collection<PaymentDatailsEntity> paymentDatailsEntityCollection;
    @CheckEmail
    private Collection<EmailNotificationEntity> emailNotificationEntityCollection;
    private CustomerEntity customer;
    private LoanStatusEntity loanStatus;
    private LoanTypeEntity loanType;
    private Long id;
    @NotNull
    private Long idCustomer;
    @NotNull
    private Long idLoanStatus;
    @NotNull
    private Long idLoanType;
    @NotNull
    private BigDecimal loanAmount;
    @NotNull
    private BigDecimal interestRate;
    @NotNull
    private BigDecimal payment;
    @Size(max = 1024)
    @NotBlank
    private String collateral;
    @NotNull
    private BigDecimal prepaymentPenalty;
    @NotNull
    private BigDecimal defaultInterestRate;

    public LoanContractDto() {
    }

    public void setLoanTerm(long loanTerm) {
        this.loanTerm = loanTerm;
    }

    public long getLoanTerm() {
        return this.loanTerm;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public java.sql.Date getDate() {
        return this.date;
    }

    public void setDateOfMaturity(java.sql.Date dateOfMaturity) {
        this.dateOfMaturity = dateOfMaturity;
    }

    public java.sql.Date getDateOfMaturity() {
        return this.dateOfMaturity;
    }

    public void setLoanPurpose(long loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public long getLoanPurpose() {
        return this.loanPurpose;
    }

    public void setPhoneNotificationEntityCollection(java.util.Collection<com.isiweekloan.entity.PhoneNotificationEntity> phoneNotificationEntityCollection) {
        this.phoneNotificationEntityCollection = phoneNotificationEntityCollection;
    }

    public java.util.Collection<com.isiweekloan.entity.PhoneNotificationEntity> getPhoneNotificationEntityCollection() {
        return this.phoneNotificationEntityCollection;
    }

    public void setWhatsappNotificationEntityCollection(java.util.Collection<com.isiweekloan.entity.WhatsappNotificationEntity> whatsappNotificationEntityCollection) {
        this.whatsappNotificationEntityCollection = whatsappNotificationEntityCollection;
    }

    public java.util.Collection<com.isiweekloan.entity.WhatsappNotificationEntity> getWhatsappNotificationEntityCollection() {
        return this.whatsappNotificationEntityCollection;
    }

    public void setPaymentDatailsEntityCollection(java.util.Collection<com.isiweekloan.entity.PaymentDatailsEntity> paymentDatailsEntityCollection) {
        this.paymentDatailsEntityCollection = paymentDatailsEntityCollection;
    }

    public java.util.Collection<com.isiweekloan.entity.PaymentDatailsEntity> getPaymentDatailsEntityCollection() {
        return this.paymentDatailsEntityCollection;
    }

    public void setEmailNotificationEntityCollection(java.util.Collection<com.isiweekloan.entity.EmailNotificationEntity> emailNotificationEntityCollection) {
        this.emailNotificationEntityCollection = emailNotificationEntityCollection;
    }

    public java.util.Collection<com.isiweekloan.entity.EmailNotificationEntity> getEmailNotificationEntityCollection() {
        return this.emailNotificationEntityCollection;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public CustomerEntity getCustomer() {
        return this.customer;
    }

    public void setLoanStatus(LoanStatusEntity loanStatus) {
        this.loanStatus = loanStatus;
    }

    public LoanStatusEntity getLoanStatus() {
        return this.loanStatus;
    }

    public void setLoanType(LoanTypeEntity loanType) {
        this.loanType = loanType;
    }

    public LoanTypeEntity getLoanType() {
        return this.loanType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Long getIdCustomer() {
        return this.idCustomer;
    }

    public void setIdLoanStatus(Long idLoanStatus) {
        this.idLoanStatus = idLoanStatus;
    }

    public Long getIdLoanStatus() {
        return this.idLoanStatus;
    }

    public void setIdLoanType(Long idLoanType) {
        this.idLoanType = idLoanType;
    }

    public Long getIdLoanType() {
        return this.idLoanType;
    }

    public void setLoanAmount(java.math.BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public java.math.BigDecimal getLoanAmount() {
        return this.loanAmount;
    }

    public void setInterestRate(java.math.BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public java.math.BigDecimal getInterestRate() {
        return this.interestRate;
    }

    public void setPayment(java.math.BigDecimal payment) {
        this.payment = payment;
    }

    public java.math.BigDecimal getPayment() {
        return this.payment;
    }

    public void setCollateral(String collateral) {
        this.collateral = collateral;
    }

    public String getCollateral() {
        return this.collateral;
    }

    public void setPrepaymentPenalty(java.math.BigDecimal prepaymentPenalty) {
        this.prepaymentPenalty = prepaymentPenalty;
    }

    public java.math.BigDecimal getPrepaymentPenalty() {
        return this.prepaymentPenalty;
    }

    public void setDefaultInterestRate(java.math.BigDecimal defaultInterestRate) {
        this.defaultInterestRate = defaultInterestRate;
    }

    public java.math.BigDecimal getDefaultInterestRate() {
        return this.defaultInterestRate;
    }
}