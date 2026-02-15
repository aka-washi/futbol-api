package com.eagle.futbolapi.features.person.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.Gender;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.person.dto.PersonDto;
import com.eagle.futbolapi.features.person.entity.Person;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper extends BaseMapper<Person, PersonDto> {

  @Mapping(target = "birthCountryId", source = "birthCountry.id")
  @Mapping(target = "birthCountryDisplayName", source = "birthCountry.displayName")
  @Mapping(target = "nationalityCountryId", source = "nationality.id")
  @Mapping(target = "nationalityCountryDisplayName", source = "nationality.displayName")
  @Mapping(target = "gender", expression = "java(genderToString(person.getGender()))")
  PersonDto toDto(Person person);

  @Mapping(target = "uniqueRegKey", ignore = true)
  @Mapping(target = "birthCountry", ignore = true)
  @Mapping(target = "nationality", ignore = true)
  @Mapping(target = "gender", expression = "java(stringToGender(personDto.getGender()))")
  Person toEntity(PersonDto personDto);

  default String genderToString(Gender gender) {
    return gender != null ? gender.getLabel() : null;
  }

  default Gender stringToGender(String gender) {
    if (gender == null)
      return null;
    for (Gender g : Gender.values()) {
      if (g.getLabel().equals(gender)) {
        return g;
      }
    }
    throw new IllegalArgumentException("Invalid gender: " + gender);
  }
}
