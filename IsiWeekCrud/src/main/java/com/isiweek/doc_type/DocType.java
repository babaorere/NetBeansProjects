package com.isiweek.doc_type;

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
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class DocType {

    public static DocType generateRandom() {
        DocType docType = new DocType();
        Faker faker = new Faker();

        docType.setName(faker.lorem().word());
        docType.setDescription(faker.lorem().sentence());
        docType.setPersons(new HashSet<>());
        docType.setDateCreated(OffsetDateTime.now());
        docType.setLastUpdated(OffsetDateTime.now());

        return docType;
    }

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 128)
    private String name;

    @Column(nullable = false, name = "\"description\"", length = 512)
    private String description;

    @OneToMany(mappedBy = "docType")
    private Set<Person> persons;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
