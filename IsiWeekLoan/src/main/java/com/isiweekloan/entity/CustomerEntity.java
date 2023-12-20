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
@Table(name = "customer", schema = "isiweekloanservices", catalog = "")
public class CustomerEntity {

    @Basic(optional = false)
    @Column(name = "credit_score")
    private Long creditScore;

    @JoinColumn(name = "person", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PersonEntity person;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCustomer", fetch = FetchType.LAZY)
    private Collection<LoanContractEntity> loanContractEntityCollection;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "id_person", nullable = false)
    private Long idPerson;

    @Basic
    @Column(name = "max_loan_amount", nullable = false, precision = 2)
    private BigDecimal maxLoanAmount;

    @Basic
    @Column(name = "observations", nullable = false, length = 256)
    private String observations;

    @Override
    public int hashCode() {
        return Objects.hash(id, idPerson, creditScore, maxLoanAmount, observations);
    }

    @Override
    public boolean equals(final Object inO) {
        if (this == inO) {
            return true;
        }
        if (inO == null || getClass() != inO.getClass()) {
            return false;
        }
        CustomerEntity that = (CustomerEntity) inO;
        return Objects.equals(id, that.id) && Objects.equals(idPerson, that.idPerson) && Objects.equals(creditScore, that.creditScore) && Objects.equals(maxLoanAmount, that.maxLoanAmount) && Objects.equals(observations, that.observations);
    }

}
