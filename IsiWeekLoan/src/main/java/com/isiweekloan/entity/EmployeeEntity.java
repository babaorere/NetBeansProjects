package com.isiweekloan.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
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
@Table(name = "employee", schema = "isiweekloanservices", catalog = "")
public class EmployeeEntity {

    @Basic(optional = false)
    @Column(name = "date_of_hire")
    @Temporal(TemporalType.DATE)
    private java.util.Date dateOfHire;

    @JoinColumn(name = "department", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DepartamentEntity department;

    @JoinColumn(name = "employee_status", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmployeeStatusEntity employeeStatus;

    @JoinColumn(name = "job_title", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private JobTitleEntity jobtitle;

    @JoinColumn(name = "person", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PersonEntity person;

    @JoinColumn(name = "manager", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PersonEntity manager;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "id_person", nullable = false)
    private Long idPerson;

    @Basic
    @Column(name = "id_employee_status", nullable = false)
    private Long idEmployeeStatus;

    @Basic
    @Column(name = "id_Job_title", nullable = false)
    private Long idJobTitle;

    @Basic
    @Column(name = "id_department", nullable = false)
    private Long idDepartment;

    @Basic
    @Column(name = "id_manager", nullable = false)
    private Long idManager;

    @Basic
    @Column(name = "salary", nullable = false, precision = 2)
    private BigDecimal salary;

    @Basic
    @Column(name = "benefits", nullable = false, length = 512)
    private String benefits;

    @Basic
    @Column(name = "contact_information", nullable = false, length = 512)
    private String contactInformation;

    @Basic
    @Column(name = "education", nullable = false, length = 512)
    private String education;

    @Basic
    @Column(name = "skills", nullable = false, length = 512)
    private String skills;

    @Basic
    @Column(name = "performance_reviews", nullable = false, length = 1024)
    private String performanceReviews;

    @Override
    public int hashCode() {
        return Objects.hash(id, idPerson, idEmployeeStatus, idJobTitle, idDepartment, idManager, dateOfHire, salary, benefits, contactInformation, education, skills, performanceReviews);
    }

    @Override
    public boolean equals(final Object inO) {
        if (this == inO) {
            return true;
        }
        if (inO == null || getClass() != inO.getClass()) {
            return false;
        }
        EmployeeEntity that = (EmployeeEntity) inO;
        return Objects.equals(id, that.id) && Objects.equals(idPerson, that.idPerson) && Objects.equals(idEmployeeStatus, that.idEmployeeStatus) && Objects.equals(idJobTitle, that.idJobTitle) && Objects.equals(idDepartment, that.idDepartment) && Objects.equals(idManager, that.idManager) && Objects.equals(dateOfHire, that.dateOfHire) && Objects.equals(salary, that.salary) && Objects.equals(benefits, that.benefits) && Objects.equals(contactInformation, that.contactInformation) && Objects.equals(education, that.education) && Objects.equals(skills, that.skills) && Objects.equals(performanceReviews, that.performanceReviews);
    }
}
