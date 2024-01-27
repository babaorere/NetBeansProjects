package com.isiweekloan.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
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
@Table(name = "payment_datails", schema = "isiweekloanservices", catalog = "")
public class PaymentDatailsEntity {

    @Basic(optional = false)
    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    private java.util.Date paymentDate;

    @JoinColumn(name = "loan_contract", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LoanContractEntity loanContract;

    @JoinColumn(name = "payment_status", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PaymentStatusEntity paymentStatus;

    @JoinColumn(name = "payment_type", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PaymentTypeEntity paymentType;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "id_loan_contract", nullable = false)
    private Long idLoanContract;

    @Basic
    @Column(name = "payment_amount", nullable = false, precision = 2)
    private BigDecimal paymentAmount;

    @Basic
    @Column(name = "id_payment_type", nullable = false)
    private Long idPaymentType;

    @Basic
    @Column(name = "id_payment_status", nullable = false)
    private Long idPaymentStatus;

    @Basic
    @Column(name = "notes", length = 1024, nullable = false)
    private String notes;

    @Override
    public int hashCode() {
        return Objects.hash(id, idLoanContract, paymentDate, paymentAmount, idPaymentType, idPaymentStatus);
    }

    @Override
    public boolean equals(final Object inO) {
        if (this == inO) {
            return true;
        }
        if (inO == null || getClass() != inO.getClass()) {
            return false;
        }
        PaymentDatailsEntity that = (PaymentDatailsEntity) inO;
        return Objects.equals(id, that.id) && Objects.equals(idLoanContract, that.idLoanContract) && Objects.equals(paymentDate, that.paymentDate) && Objects.equals(paymentAmount, that.paymentAmount) && Objects.equals(idPaymentType, that.idPaymentType) && Objects.equals(idPaymentStatus, that.idPaymentStatus);
    }

}
