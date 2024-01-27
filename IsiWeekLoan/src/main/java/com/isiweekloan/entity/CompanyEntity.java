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
@Table(name = "company", schema = "isiweekloanservices", catalog = "")
public class CompanyEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.LAZY)
    private Collection<PersonEntity> personEntityCollection;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Company name cannot be empty.")
    @Size(min = 3, max = 128, message = "Country name must be between 3 and 128 characters.")
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @NotBlank(message = "Tax Identification Number cannot be empty.")
    @Size(max = 32, message = "Tax Identification Number cannot exceed 32 characters.")
    @Column(name = "taxidnumber", nullable = false, length = 32)
    private String taxidNumber;

    @NotBlank(message = "Company description cannot be empty.")
    @Size(max = 512, message = "Company description cannot exceed 512 characters.")
    @Column(name = "description", nullable = false, length = 512)
    private String description;

    @NotBlank(message = "Address cannot be empty.")
    @Size(max = 256, message = "Address cannot exceed 256 characters.")
    @Column(name = "address", nullable = false, length = 256)
    private String address;

    @NotBlank(message = "Phone1 cannot be empty.")
    @Size(max = 32, message = "Phone1 cannot exceed 32 characters.")
    @Column(name = "phone1", nullable = false, length = 32)
    private String phone1;

    @Size(max = 32, message = "Phone2 cannot exceed 32 characters.")
    @Column(name = "phone2", nullable = true, length = 32)
    private String phone2;

    @NotBlank(message = "Email cannot be empty.")
    @Size(max = 32, message = "Email cannot exceed 32 characters.")
    @Column(name = "email", nullable = false, length = 32)
    private String email;

    @NotBlank(message = "Primary Contact cannot be empty.")
    @Size(max = 256, message = "Primary Contact cannot exceed 256 characters.")
    @Column(name = "primary_contact", nullable = false, length = 256)
    private String primaryContact;

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(final Object inO) {
        if (this == inO) {
            return true;
        }
        if (inO == null || getClass() != inO.getClass()) {
            return false;
        }

        CompanyEntity that = (CompanyEntity) inO;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

}
