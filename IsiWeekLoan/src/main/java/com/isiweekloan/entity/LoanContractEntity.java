package com.isiweekloan.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loan_contract", schema = "isiweekloanservices", catalog = "")
public class LoanContractEntity {

    @Basic(optional = false)
    @Column(name = "loan_term")
    private long loanTerm;

    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private java.sql.Date date;

    @Basic(optional = false)
    @Column(name = "date_of_maturity")
    @Temporal(TemporalType.DATE)
    private java.sql.Date dateOfMaturity;

    @Basic(optional = false)
    @Column(name = "loan_purpose")
    private long loanPurpose;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLoanContract", fetch = FetchType.LAZY)
    private Collection<PhoneNotificationEntity> phoneNotificationEntityCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLoanContract", fetch = FetchType.LAZY)
    private Collection<WhatsappNotificationEntity> whatsappNotificationEntityCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLoanContract", fetch = FetchType.LAZY)
    private Collection<PaymentDatailsEntity> paymentDatailsEntityCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLoanContract", fetch = FetchType.LAZY)
    private Collection<EmailNotificationEntity> emailNotificationEntityCollection;

    @JoinColumn(name = "customer", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CustomerEntity customer;

    @JoinColumn(name = "loan_status", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LoanStatusEntity loanStatus;

    @JoinColumn(name = "loan_type", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LoanTypeEntity loanType;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "id_customer", nullable = false)
    private Long idCustomer;

    @Basic
    @Column(name = "id_loan_status", nullable = false)
    private Long idLoanStatus;

    @Basic
    @Column(name = "id_loan_type", nullable = false)
    private Long idLoanType;

    @Basic
    @Column(name = "loan_amount", nullable = false, precision = 2)
    private BigDecimal loanAmount;

    @Basic
    @Column(name = "interest_rate", nullable = false, precision = 2)
    private BigDecimal interestRate;

    @Basic
    @Column(name = "payment", nullable = false, precision = 2)
    private BigDecimal payment;

    @Basic
    @Column(name = "collateral", nullable = false, length = 1024)
    private String collateral;

    @Basic
    @Column(name = "prepayment_penalty", nullable = false, precision = 2)
    private BigDecimal prepaymentPenalty;

    @Basic
    @Column(name = "default_interest_rate", nullable = false, precision = 2)
    private BigDecimal defaultInterestRate;

    @Override
    public int hashCode() {
        return Objects.hash(id, idCustomer, idLoanStatus, idLoanType, loanAmount, interestRate, loanTerm, payment, date, dateOfMaturity, loanPurpose, collateral, prepaymentPenalty, defaultInterestRate);
    }

    @Override
    public boolean equals(final Object inO) {
        if (this == inO) {
            return true;
        }
        if (inO == null || getClass() != inO.getClass()) {
            return false;
        }
        LoanContractEntity that = (LoanContractEntity) inO;
        return Objects.equals(id, that.id) && Objects.equals(idCustomer, that.idCustomer) && Objects.equals(idLoanStatus, that.idLoanStatus) && Objects.equals(idLoanType, that.idLoanType) && Objects.equals(loanAmount, that.loanAmount) && Objects.equals(interestRate, that.interestRate) && Objects.equals(loanTerm, that.loanTerm) && Objects.equals(payment, that.payment) && Objects.equals(date, that.date) && Objects.equals(dateOfMaturity, that.dateOfMaturity) && Objects.equals(loanPurpose, that.loanPurpose) && Objects.equals(collateral, that.collateral) && Objects.equals(prepaymentPenalty, that.prepaymentPenalty) && Objects.equals(defaultInterestRate, that.defaultInterestRate);
    }

}
