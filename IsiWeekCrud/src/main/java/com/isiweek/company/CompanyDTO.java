package com.isiweek.company;

import com.isiweek.person.model.PersonDTO;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CompanyDTO {

    private Long id;
    private String name;
    private String description;
    private String taxidnumber;
    private String address;
    private String email;
    private String phone1;
    private String phone2;
    private String primaryContact;
    private OffsetDateTime dateCreated;
    private OffsetDateTime lastUpdated;
    private Set<PersonDTO> persons = new HashSet<>();
    private CompanyStatusDTO status;

    // Soft copy contructor
    public CompanyDTO(CompanyDTO inDTO) {
        // Copia los valores a la instancia actual
        BeanUtils.copyProperties(inDTO, this);
    }

    // DTO copy constructor
    public CompanyDTO(Company inEntity) {

        // Transforma el parametro, a una clase entity
        CompanyDTO dto = CompanyMapper.INSTANCE.mapToDTO(inEntity);

        // Copia los valores mapeados a la instancia actual
        BeanUtils.copyProperties(dto, this);
    }

    @Override
    public String toString() {
        return "[\"id\": " + id + ", \"name\": " + name + "]";
    }

    @Override
    public boolean equals(final Object inO) {
        if (this == inO) {
            return true;
        }

        if (inO == null || getClass() != inO.getClass()) {
            return false;
        }

        CompanyDTO company = (CompanyDTO) inO;
        return Objects.equals(getId(), company.getId()) && Objects.equals(getName(), company.getName()) && Objects.equals(getTaxidnumber(), company.getTaxidnumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getTaxidnumber());
    }

    public void addPerson(PersonDTO inPersonDTO) {
        persons.add(inPersonDTO);
        inPersonDTO.getCompanies().add(this);
    }

    public static CompanyDTO genRandom() {
        Long id = null;
        String address = "Company Address " + UUID.randomUUID();
        String description = "Company Description " + UUID.randomUUID();
        String email = UUID.randomUUID() + "@example.com";
        String name = "Company Name " + UUID.randomUUID();
        String phone1 = "Phone1 " + UUID.randomUUID().toString();
        String phone2 = "Phone2 " + UUID.randomUUID().toString();
        String primaryContact = "Primary Contact" + UUID.randomUUID();
        String taxidnumber = "TaxIDnumber" + UUID.randomUUID().toString();
        Set<PersonDTO> persons = new HashSet<>();

        // Crear instancia utilizando el constructor con todos los argumentos
        return new CompanyDTO(id, name, description, taxidnumber, address, email, phone1, phone2,
            primaryContact, null, null, persons, null);

    }

    public Company toEntity() {
        return CompanyMapper.INSTANCE.mapToEntity(this);
    }

}
