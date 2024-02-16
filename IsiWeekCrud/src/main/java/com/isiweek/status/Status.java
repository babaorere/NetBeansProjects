package com.isiweek.status;

import com.isiweek.company.Company;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
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
public class Status {

    public static Status randomGenerator() {
        StatusEnum randomValue = StatusEnum.values()[new Random().nextInt(StatusEnum.values().length)];
        return Status.builder().id(0L).statusEnum(randomValue).build();
    }

    public static Status emptyGenerator() {
        return Status.builder().id(0L).statusEnum(StatusEnum.PENDING).build();
    }

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Builder.Default
    private Long id = null;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    @NonNull
    @NotNull(message = "StatusEnum is required")
    @Builder.Default
    private StatusEnum statusEnum = StatusEnum.PENDING;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Builder.Default
    private OffsetDateTime dateCreated = OffsetDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    @Builder.Default
    private OffsetDateTime lastUpdated = OffsetDateTime.now();

    @OneToMany(mappedBy = "status", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Company> companies = new HashSet<Company>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Status status = (Status) o;
        return Objects.equals(id, status.getId()) && Objects.equals(statusEnum, status.getStatusEnum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statusEnum);
    }

    @Override
    public String toString() {
        return "[\"id\": " + id + ", \"statusEnum\": " + statusEnum + "]";
    }

}
