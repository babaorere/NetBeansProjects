package com.isiweekloan.dto;

import com.isiweekloan.entity.PersonEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;

@ApiModel()
public class DocTypeDto extends AbstractDto<Collection<PersonEntity>> {
    private Collection<PersonEntity> personEntityCollection;
    private Long id;
    @Size(max = 128)
    @NotBlank
    private String name;
    @Size(max = 512)
    @NotBlank
    private String description;

    public DocTypeDto() {
    }

    public void setPersonEntityCollection(java.util.Collection<com.isiweekloan.entity.PersonEntity> personEntityCollection) {
        this.personEntityCollection = personEntityCollection;
    }

    public java.util.Collection<com.isiweekloan.entity.PersonEntity> getPersonEntityCollection() {
        return this.personEntityCollection;
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