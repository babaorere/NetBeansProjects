package com.isiweek.marital_status;

import net.datafaker.Faker;
import com.isiweek.person.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaritalStatus {

    public static MaritalStatus generateRandom() {
        MaritalStatus maritalStatus = new MaritalStatus();
        Faker faker = new Faker();

        maritalStatus.setName(faker.name().title());
        maritalStatus.setDescription(faker.lorem().sentence());
        maritalStatus.setPersons(new HashSet<>());  // Ajusta según tu lógica para inicializar el conjunto
        maritalStatus.setDateCreated(OffsetDateTime.now());
        maritalStatus.setLastUpdated(OffsetDateTime.now());

        return maritalStatus;
    }

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 128)
    private String name;

    @Column(name = "\"description\"", length = 512)
    private String description;

    @OneToMany(mappedBy = "maritalStatus")
    private Set<Person> persons;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
