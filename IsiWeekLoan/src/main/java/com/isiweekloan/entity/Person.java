package com.isiweekloan.entity;

import com.isiweekloan.security.model.Audit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "person")
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "date_of_birth", nullable = false)
    private String dateOfBirth;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "ssn", nullable = false)
    private String ssn;

    @Column(name = "marital_status", nullable = false)
    private String maritalStatus;

    @Column(name = "employment_status", nullable = false)
    private String employmentStatus;

    @Column(name = "annual_income", nullable = false)
    private Integer annualIncome;

    @Column(name = "credit_score", nullable = false)
    private Integer creditScore;

    @Column(name = "employment_length", nullable = false)
    private Integer employmentLength;

    @Column(name = "home_ownership", nullable = false)
    private String homeOwnership;

    @Column(name = "number_of_dependents", nullable = false)
    private Integer numberOfDependents;

    @Column(name = "education_level", nullable = false)
    private String educationLevel;

    @Column(name = "criminal_record", nullable = false)
    private Boolean criminalRecord;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private User user;

}
