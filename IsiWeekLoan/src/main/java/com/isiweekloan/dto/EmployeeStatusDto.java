package com.isiweekloan.dto;

import com.isiweekloan.entity.EmployeeEntity;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collection;

@ApiModel()
public class EmployeeStatusDto extends AbstractDto<Collection<EmployeeEntity>> {
    private Collection<EmployeeEntity> employeeEntityCollection;
    private Long id;
    @Size(max = 128)
    @NotBlank
    private String name;
    @Size(max = 512)
    @NotBlank
    private String description;

    public EmployeeStatusDto() {
    }

    public void setEmployeeEntityCollection(java.util.Collection<com.isiweekloan.entity.EmployeeEntity> employeeEntityCollection) {
        this.employeeEntityCollection = employeeEntityCollection;
    }

    public java.util.Collection<com.isiweekloan.entity.EmployeeEntity> getEmployeeEntityCollection() {
        return this.employeeEntityCollection;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}