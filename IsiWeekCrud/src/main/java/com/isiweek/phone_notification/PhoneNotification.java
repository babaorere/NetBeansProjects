package com.isiweek.phone_notification;

import com.isiweek.loan_contract.LoanContract;
import com.isiweek.person.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class PhoneNotification {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime dateOfCall;

    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @Column(nullable = false, length = 256)
    private String sentAt;

    @Column(nullable = false, length = 256)
    private String subject;

    @Column(nullable = false, length = 1024)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_contract_id", nullable = false)
    private LoanContract loanContract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
