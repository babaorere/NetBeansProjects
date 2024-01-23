package com.isiweek.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CompanyStatusDTO {

    private Long id;
    private String name;

    public CompanyStatusDTO(CompanyStatus inCompanyStatus) {
        this.id = inCompanyStatus.getId();
        this.name = inCompanyStatus.getName();
    }

    @Override
    public String toString() {
        return "[\"id\": " + id + ", \"statusName\": " + name + "]";
    }

    public CompanyStatus mapToEntity() {
        return mapToEntity(this,
            new CompanyStatus());
    }

    public static CompanyStatus mapToEntity(final CompanyStatusDTO inDTO, final CompanyStatus inEntity) {

        inEntity.setId(inDTO.getId());
        inEntity.setName(inDTO.getName());

        return inEntity;
    }
}
