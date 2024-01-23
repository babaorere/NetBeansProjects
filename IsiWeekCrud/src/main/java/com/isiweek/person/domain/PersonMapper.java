package com.isiweek.person.domain;

import com.isiweek.person.model.PersonDTO;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO mapToDTO(Person entity);

    Set<PersonDTO> mapToDTOSet(Set<Person> entitySet);

    @Mapping(ignore = true)
    Person mapToEntity(PersonDTO dto);

    Set<Person> mapToEntitySet(Set<PersonDTO> dtoSet);
}
