package com.isiweek.loan_contract.domain;

import com.isiweek.customer.Customer;
import com.isiweek.email_notification.EmailNotification;
import com.isiweek.loan_status.domain.LoanStatus;
import com.isiweek.loan_type.domain.LoanType;
import com.isiweek.payment_datails.domain.PaymentDatails;
import com.isiweek.phone_notification.domain.PhoneNotification;
import com.isiweek.whatsapp_notification.domain.WhatsappNotification;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class LoanContract {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 2, scale = 2)
    private BigDecimal loanAmount;

    @Column(nullable = false, precision = 2, scale = 2)
    private BigDecimal interestRate;

    @Column(nullable = false)
    private Long loanTerm;

    @Column(nullable = false, precision = 2, scale = 2)
    private BigDecimal payment;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalDate dateOfMaturity;

    @Column(nullable = false)
    private Long loanPurpose;

    @Column(length = 1024)
    private String collateral;

    @Column(precision = 2, scale = 2)
    private BigDecimal prepaymentPenalty;

    @Column(precision = 2, scale = 2)
    private BigDecimal defaultInterestRate;

    @OneToMany(mappedBy = "loanContract")
    private Set<EmailNotification> loanContractEmailNotifications;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_type_id", nullable = false)
    private LoanType loanType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_status_id", nullable = false)
    private LoanStatus loanStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "loanContract")
    private Set<PaymentDatails> loanContractPaymentDatailses;

    @OneToMany(mappedBy = "loanContract")
    private Set<PhoneNotification> loanContractPhoneNotifications;

    @OneToMany(mappedBy = "loanContract")
    private Set<WhatsappNotification> loanContractWhatsappNotifications;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
