package com.isiweek.company;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CompanyStatus {

    public static CompanyStatus genRandom() {
        return new CompanyStatus(null, UUID.randomUUID() + " NONE");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 64)
    private String name;

    // DTO copy constructor
    public CompanyStatus(CompanyStatusDTO inDTO) {

        // Transforma el parametro, a una clase entity
        CompanyStatus entity = CompanyStatusMapper.INSTANCE.mapToEntity(inDTO);

        // Copia los valores mapeados a la instancia actual
        BeanUtils.copyProperties(entity, this);
    }

    // Otros campos relacionados con el estado de la compañía
    // Constructores, getters y setters según sea necesario
    @Override
    public String toString() {
        return "[\"id\": " + id + ", \"statusName\": " + name + "]";
    }

    @Override
    public boolean equals(final Object inO) {
        if (this == inO) {
            return true;
        }

        if (inO == null || getClass() != inO.getClass()) {
            return false;
        }

        CompanyStatus companyStatus = (CompanyStatus) inO;
        return Objects.equals(getId(), companyStatus.getId()) && Objects.equals(getName(), companyStatus.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    public CompanyStatusDTO mapToDTO() {
        return CompanyStatusMapper.INSTANCE.mapToDTO(this);
    }

}
