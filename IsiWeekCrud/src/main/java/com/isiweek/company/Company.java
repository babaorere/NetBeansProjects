package com.isiweek.company;

import com.isiweek.person.domain.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull(message = "Name is required")
    @Column(nullable = false, unique = true, length = 128)
    private String name;

    @NonNull
    @NotNull(message = "Field is required")
    @Column(nullable = false, name = "\"description\"", length = 512)
    private String description;

    @NonNull
    @Column(nullable = false, unique = true, length = 64)
    private String taxidnumber;

    @NonNull
    @NotNull(message = "Address is required")
    @Column(nullable = false, length = 256)
    private String address;

    @NonNull
    @NotNull(message = "Email is required")
    @Column(nullable = false, unique = true, length = 64)
    private String email;

    @NonNull
    @NotNull(message = "Phone1 is required")
    @Column(nullable = false, length = 32)
    private String phone1;

    @Column(length = 32)
    private String phone2;

    @NonNull
    @NotNull(message = "Primary Contact is required")
    @Column(nullable = false, length = 256)
    private String primaryContact;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @NonNull
    @NotNull(message = "Persons is required")
    @Builder.Default
    @ManyToMany(mappedBy = "companies")
    private Set<Person> persons = new HashSet<>();

    @NonNull
    @NotNull(message = "Status is required")
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private CompanyStatus status;

    // Soft copy contructor
    public Company(Company inEntity) {
        // Copia los valores a la instancia actual
        BeanUtils.copyProperties(inEntity, this);
    }

    // DTO copy constructor
    public Company(CompanyDTO inDTO) {

        // Transforma el parametro, a una clase entity
        Company entity = CompanyMapper.INSTANCE.mapToEntity(inDTO);

        // Copia los valores mapeados a la instancia actual
        BeanUtils.copyProperties(entity, this);
    }

    @Override
    public String toString() {
        return "[\"id\": " + id + ", \"name\": " + name + "]";
    }

    @Override
    public boolean equals(final Object inO) {
        if (this == inO) {
            return true;
        }
        if (inO == null || getClass() != inO.getClass()) {
            return false;
        }
        Company company = (Company) inO;
        return Objects.equals(getId(), company.getId()) && Objects.equals(getName(), company.getName()) && Objects.equals(getTaxidnumber(), company.getTaxidnumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getTaxidnumber());
    }

    public void addPerson(Person inPerson) {
        persons.add(inPerson);
        inPerson.getCompanies().add(this);
    }

    public static Company genRandom() {
        Long id = 1L;
        String address = "Company Address " + UUID.randomUUID();
        String description = "Company Description " + UUID.randomUUID();
        String email = UUID.randomUUID() + "@example.com";
        String name = "Company Name " + UUID.randomUUID();
        String phone1 = "Phone1 " + UUID.randomUUID().toString();
        String phone2 = "Phone2 " + UUID.randomUUID().toString();
        String primaryContact = "Primary Contact" + UUID.randomUUID();
        String taxidnumber = "TaxIDnumber" + UUID.randomUUID().toString();
        Set<Person> persons = new HashSet<>();
        CompanyStatus status = new CompanyStatus(1L, "NONE");

        // Crear instancia utilizando el constructor con todos los argumentos
        return new Company(id, name, description, taxidnumber, address, email, phone1, phone2,
            primaryContact, null, null, persons, status);

    }

    public CompanyDTO toDTO() {
        return CompanyMapper.INSTANCE.mapToDTO(this);
    }

}
