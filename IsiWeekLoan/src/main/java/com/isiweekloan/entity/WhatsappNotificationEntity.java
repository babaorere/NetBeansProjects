package com.isiweekloan.entity;

import jakarta.persistence.*;
import java.util.Date;
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
@Table(name = "whatsapp_notification", schema = "isiweekloanservices", catalog = "")
public class WhatsappNotificationEntity {

    @Basic(optional = false)
    @Column(name = "date_sent")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSent;

    @JoinColumn(name = "loan_contract", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LoanContractEntity loanContract;

    @JoinColumn(name = "person", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PersonEntity person;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "id_person", nullable = false)
    private Long idPerson;

    @Basic
    @Column(name = "id_loan_contract", nullable = false)
    private Long idLoanContract;

    @Basic
    @Column(name = "subject", nullable = false, length = 256)
    private String subject;

    @Basic
    @Column(name = "sent_at", nullable = false, length = 256)
    private String sentAt;

    @Basic
    @Column(name = "body", nullable = false, length = 1024)
    private String body;

    @Override
    public int hashCode() {
        return Objects.hash(id, idPerson, idLoanContract, subject, sentAt, body, dateSent);
    }

    @Override
    public boolean equals(final Object inO) {
        if (this == inO) {
            return true;
        }
        if (inO == null || getClass() != inO.getClass()) {
            return false;
        }
        WhatsappNotificationEntity that = (WhatsappNotificationEntity) inO;
        return Objects.equals(id, that.id) && Objects.equals(idPerson, that.idPerson) && Objects.equals(idLoanContract, that.idLoanContract) && Objects.equals(subject, that.subject) && Objects.equals(sentAt, that.sentAt) && Objects.equals(body, that.body) && Objects.equals(dateSent, that.dateSent);
    }

}
