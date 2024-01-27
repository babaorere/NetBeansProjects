package com.isiweekloan.entity;

import jakarta.persistence.*;
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
@Table(name = "loan_collector_status", schema = "isiweekloanservices", catalog = "")
public class LoanCollectorStatusEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLcStatus", fetch = FetchType.LAZY)
    private Collection<LoanCollectorEntity> loanCollectorEntityCollection;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Basic
    @Column(name = "description", nullable = false, length = 512)
    private String description;

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public boolean equals(final Object inO) {
        if (this == inO) {
            return true;
        }
        if (inO == null || getClass() != inO.getClass()) {
            return false;
        }
        LoanCollectorStatusEntity that = (LoanCollectorStatusEntity) inO;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

}
