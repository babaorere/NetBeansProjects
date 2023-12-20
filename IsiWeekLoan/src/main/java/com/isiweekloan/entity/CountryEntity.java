package com.isiweekloan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Table(name = "country", schema = "isiweekloanservices", catalog = "")
public class CountryEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCountry", fetch = FetchType.LAZY)
    private Collection<PersonEntity> personEntityCollection;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Country name cannot be empty.")
    @Size(min = 3, max = 128, message = "Country name must be between 3 and 128 characters.")
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @NotBlank(message = "Country description cannot be empty.")
    @Size(max = 512, message = "Country description cannot exceed 512 characters.")
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
        CountryEntity that = (CountryEntity) inO;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }
}
