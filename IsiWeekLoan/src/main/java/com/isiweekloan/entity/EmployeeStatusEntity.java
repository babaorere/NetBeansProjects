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

@Table(name = "employee_status", schema = "isiweekloanservices", catalog = "")
public class EmployeeStatusEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmployeeStatus", fetch = FetchType.LAZY)
    private Collection<EmployeeEntity> employeeEntityCollection;

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
        EmployeeStatusEntity that = (EmployeeStatusEntity) inO;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

}
