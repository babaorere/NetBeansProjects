package com.isiweekloan.dto;

import com.isiweekloan.annotation.CheckDate;
import com.isiweekloan.entity.DepartamentEntity;
import com.isiweekloan.entity.EmployeeStatusEntity;
import com.isiweekloan.entity.JobTitleEntity;
import com.isiweekloan.entity.PersonEntity;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.sql.Date;

@ApiModel()
public class EmployeeDto extends AbstractDto<Date> {
    @CheckDate
    private Date dateOfHire;
    private DepartamentEntity department;
    private EmployeeStatusEntity employeeStatus;
    private JobTitleEntity jobtitle;
    private PersonEntity person;
    private PersonEntity manager;
    private Long id;
    @NotNull
    private Long idPerson;
    @NotNull
    private Long idEmployeeStatus;
    @NotNull
    private Long idJobTitle;
    @NotNull
    private Long idDepartment;
    @NotNull
    private Long idManager;
    @NotNull
    private BigDecimal salary;
    @Size(max = 512)
    @NotBlank
    private String benefits;
    @Size(max = 512)
    @NotBlank
    private String contactInformation;
    @Size(max = 512)
    @NotBlank
    private String education;
    @Size(max = 512)
    @NotBlank
    private String skills;
    @Size(max = 1024)
    @NotBlank
    private String performanceReviews;

    public EmployeeDto() {
    }

    public void setDateOfHire(java.sql.Date dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public java.util.Date getDateOfHire() {
        return this.dateOfHire;
    }

    public void setDepartment(DepartamentEntity department) {
        this.department = department;
    }

    public DepartamentEntity getDepartment() {
        return this.department;
    }

    public void setEmployeeStatus(EmployeeStatusEntity employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public EmployeeStatusEntity getEmployeeStatus() {
        return this.employeeStatus;
    }

    public void setJobtitle(JobTitleEntity jobtitle) {
        this.jobtitle = jobtitle;
    }

    public JobTitleEntity getJobtitle() {
        return this.jobtitle;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    public PersonEntity getPerson() {
        return this.person;
    }

    public void setManager(PersonEntity manager) {
        this.manager = manager;
    }

    public PersonEntity getManager() {
        return this.manager;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public Long getIdPerson() {
        return this.idPerson;
    }

    public void setIdEmployeeStatus(Long idEmployeeStatus) {
        this.idEmployeeStatus = idEmployeeStatus;
    }

    public Long getIdEmployeeStatus() {
        return this.idEmployeeStatus;
    }

    public void setIdJobTitle(Long idJobTitle) {
        this.idJobTitle = idJobTitle;
    }

    public Long getIdJobTitle() {
        return this.idJobTitle;
    }

    public void setIdDepartment(Long idDepartment) {
        this.idDepartment = idDepartment;
    }

    public Long getIdDepartment() {
        return this.idDepartment;
    }

    public void setIdManager(Long idManager) {
        this.idManager = idManager;
    }

    public Long getIdManager() {
        return this.idManager;
    }

    public void setSalary(java.math.BigDecimal salary) {
        this.salary = salary;
    }

    public java.math.BigDecimal getSalary() {
        return this.salary;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getBenefits() {
        return this.benefits;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getContactInformation() {
        return this.contactInformation;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducation() {
        return this.education;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSkills() {
        return this.skills;
    }

    public void setPerformanceReviews(String performanceReviews) {
        this.performanceReviews = performanceReviews;
    }

    public String getPerformanceReviews() {
        return this.performanceReviews;
    }
}