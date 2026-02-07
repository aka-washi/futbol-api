package com.eagle.futbolapi.features.person.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.person.dto.PersonDTO;
import com.eagle.futbolapi.features.person.entity.Person;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper extends BaseMapper<Person, PersonDTO> {

  @Mapping(target = "birthCountryId", source = "birthCountry.id")
  @Mapping(target = "birthCountryDisplayName", source = "birthCountry.displayName")
  @Mapping(target = "nationalityCountryId", source = "nationality.id")
  @Mapping(target = "nationalityCountryDisplayName", source = "nationality.displayName")
  PersonDTO toDTO(Person entity);

  @Mapping(target = "birthCountry", ignore = true)
  @Mapping(target = "nationality", ignore = true)
  Person toEntity(PersonDTO dto);
}
