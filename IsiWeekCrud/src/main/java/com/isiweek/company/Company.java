package com.isiweek.company;

import com.isiweek.person.Person;
import com.isiweek.status.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import net.datafaker.Faker;
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

    public static Company generateRandom() {
        Faker faker = new Faker();

        return Company.builder()
                .name(faker.company().name())
                .description(faker.lorem().sentence())
                .taxidnumber(faker.number().digits(8))
                .address(faker.address().streetAddress())
                .email(faker.internet().emailAddress())
                .phone1(faker.phoneNumber().phoneNumber())
                .phone2(faker.phoneNumber().phoneNumber())
                .primaryContact(faker.name().fullName())
                .persons(new HashSet<>())
                .status(Status.randomGenerator())
                .build();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Builder.Default
    private Long id = 0L;

    @NonNull
    @NotNull(message = "Name is required")
    @Column(nullable = false, unique = true, length = 128)
    @Builder.Default
    private String name = "";

    @NonNull
    @NotNull(message = "Field is required")
    @Column(nullable = false, name = "description", length = 512)
    @Builder.Default
    private String description = "";

    @NonNull
    @Column(nullable = false, unique = true, length = 64)
    @Builder.Default
    private String taxidnumber = "";

    @NonNull
    @NotNull(message = "Address is required")
    @Column(nullable = false, length = 256)
    @Builder.Default
    private String address = "";

    @NonNull
    @NotNull(message = "Email is required")
    @Column(nullable = false, unique = true, length = 64)
    @Builder.Default
    private String email = "";

    @NonNull
    @NotNull(message = "Phone1 is required")
    @Column(nullable = false, length = 32)
    @Builder.Default
    private String phone1 = "";

    @Column(nullable = false, length = 32)
    @Builder.Default
    private String phone2 = "";

    @NonNull
    @NotNull(message = "Primary Contact is required")
    @Column(nullable = false, length = 256)
    @Builder.Default
    private String primaryContact = "";

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @NonNull
    @NotNull(message = "Persons is required")
    @Builder.Default
    @ManyToMany(mappedBy = "companies", fetch = FetchType.LAZY)
    private Set<Person> persons = new HashSet<>();

    @NonNull
    @NotNull(message = "Status is required")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    @Builder.Default
    private Status status = Status.emptyGenerator();

    // Soft copy contructor
    public Company(Company inEntity) {
        // Copia los valores a la instancia actual
        BeanUtils.copyProperties(inEntity, this);
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

}
