package com.isiweekloan.entity;

import jakarta.persistence.*;
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

@Table(name = "loan_collector", schema = "isiweekloanservices", catalog = "")
public class LoanCollectorEntity {

    @JoinColumn(name = "person", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PersonEntity person;

    @Basic(optional = false)
    @Column(name = "id_person")
    private long idPerson;

    @JoinColumn(name = "lc_status", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LoanCollectorStatusEntity lcStatus;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "id_lc_status", nullable = false)
    private Long idLcStatus;

    @Override
    public int hashCode() {
        return Objects.hash(id, idPerson, idLcStatus);
    }

    @Override
    public boolean equals(final Object inO) {
        if (this == inO) {
            return true;
        }
        if (inO == null || getClass() != inO.getClass()) {
            return false;
        }
        LoanCollectorEntity that = (LoanCollectorEntity) inO;
        return Objects.equals(id, that.id) && Objects.equals(idPerson, that.idPerson) && Objects.equals(idLcStatus, that.idLcStatus);
    }

}
