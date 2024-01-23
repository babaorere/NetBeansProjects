package com.isiweek.person.model;

import com.isiweek.company.CompanyDTO;
import com.isiweek.country.domain.Country;
import com.isiweek.criminal_record.domain.CriminalRecord;
import com.isiweek.doc_type.domain.DocType;
import com.isiweek.marital_status.domain.MaritalStatus;
import com.isiweek.person.domain.Person;
import com.isiweek.person.domain.PersonMapper;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
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
public class PersonDTO {

    private Long id;
    private String idDoc;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber1;
    private String phoneNumber2;
    private LocalDate dateOfBirth;
    private Boolean gender;
    private String address;
    private String city;
    private String state;
    private String observations;
    private String occupation;
    private String photo;
    private MaritalStatus maritalStatus;
    private DocType docType;
    private Country country;
    private Set<CompanyDTO> companies;
    private CriminalRecord criminalRecord;

    public PersonDTO(Person entity) {
        // Utiliza el mapper para mapear desde Person a PersonDTO
        PersonDTO mappedDTO = PersonMapper.INSTANCE.mapToDTO(entity);

        // Copia los valores mapeados a la instancia actual
        BeanUtils.copyProperties(mappedDTO, this);
    }

    public static Set<PersonDTO> mapToDTO(final Set<Person> entitySet) {
        return entitySet.stream()
            .map(PersonDTO::new)
            .collect(Collectors.toSet());
    }

    public Person mapToEntity() {
        return new Person(this);
    }

    public static Set<Person> mapToEntity(final Set<PersonDTO> dtoSet) {
        return dtoSet.stream()
            .map(Person::new)
            .collect(Collectors.toSet());
    }
}
