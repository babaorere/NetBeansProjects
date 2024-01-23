package com.isiweek.company;

import com.isiweek.person.domain.Person;
import com.isiweek.person.domain.PersonMapper;
import com.isiweek.person.model.PersonDTO;
import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PersonMapper.class, CompanyStatusMapper.class}) // Asegúrate de ajustar a tus necesidades
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    CompanyDTO mapToDTO(Company company);

    Company mapToEntity(CompanyDTO companyDTO);

    @Mapping(target = "persons", source = "persons", qualifiedByName = "mapToDTOSet")
    CompanyDTO mapToDTOWithPersons(Company company);

    @IterableMapping(qualifiedByName = "mapToDTO")
    Set<PersonDTO> mapToDTOSet(Set<Person> persons);
}
