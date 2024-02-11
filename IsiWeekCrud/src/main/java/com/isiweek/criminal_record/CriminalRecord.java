package com.isiweek.criminal_record;

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
public class CriminalRecord {

    public static CriminalRecord generateRandom() {
        CriminalRecord criminalRecord = new CriminalRecord();
        Faker faker = new Faker();

        criminalRecord.setName(faker.lorem().word());
        criminalRecord.setDescription(faker.lorem().sentence());
        criminalRecord.setCriminalRecordPersons(new HashSet<>());
        criminalRecord.setDateCreated(OffsetDateTime.now());
        criminalRecord.setLastUpdated(OffsetDateTime.now());

        return criminalRecord;
    }

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 128)
    private String name;

    @Column(nullable = false, name = "\"description\"", length = 512)
    private String description;

    @OneToMany(mappedBy = "criminalRecord")
    private Set<Person> criminalRecordPersons;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
