package com.isiweek.user;

import com.isiweek.debtor.Debtor;
import com.isiweek.lender.Lender;
import com.isiweek.role.Role;
import com.isiweek.status.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email")})
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lender_id")
    private Lender lender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debtor_id")
    private Debtor debtor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    public Collection<Role> getRoles() {
        return List.of(role);
    }

    public User(Long inId, String inUsername, String inEmail, String inPassword, Role inRole, Lender inLender, Debtor inDebtor, Status inStatus, OffsetDateTime inDateCreated, OffsetDateTime inLastUpdated) {
        this.id = inId;
        this.username = inEmail;
        this.email = inEmail;
        this.password = inPassword;
        this.role = inRole;
        this.lender = inLender;
        this.debtor = inDebtor;
        this.status = inStatus;
        this.dateCreated = inDateCreated;
        this.lastUpdated = inLastUpdated;
    }

    /**
     * @param inUsername the username to set
     */
    public void setUsername(String inUsername) {
        if (inUsername == null || inUsername.isBlank()) {
            return;
        }

        this.username = inUsername;
        this.email = inUsername;
    }

    /**
     * @param inEmail the email to set
     */
    public void setEmail(String inEmail) {
        if (inEmail == null || inEmail.isBlank()) {
            return;
        }

        this.username = inEmail;
        this.email = inEmail;
    }

}
