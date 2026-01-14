package com.eagle.futbolapi.features.person.mapper;

import org.springframework.stereotype.Component;

import com.eagle.futbolapi.features.country.repository.CountryRepository;
import com.eagle.futbolapi.features.person.dto.PersonDTO;
import com.eagle.futbolapi.features.person.entity.Person;

@Component
public class PersonMapper {

  private final CountryRepository countryRepository;

  public PersonMapper(CountryRepository countryRepository) {
    this.countryRepository = countryRepository;
  }

  public PersonDTO toPersonDTO(Person person) {
    if (person == null) {
      return null;
    }

    return PersonDTO.builder()
        .id(person.getId())
        .uniqueRegKey(person.getUniqueRegKey())
        .firstName(person.getFirstName())
        .middleName(person.getMiddleName())
        .lastName(person.getLastName())
        .secondLastName(person.getSecondLastName())
        .displayName(person.getDisplayName())
        .birthCountryId(person.getBirthCountry() != null ? person.getBirthCountry().getId() : null)
        .birthCountryDisplayName(person.getBirthCountry() != null ? person.getBirthCountry().getDisplayName() : null)
        .nationalityCountryId(person.getNationality() != null ? person.getNationality().getId() : null)
        .nationalityCountryDisplayName(person.getNationality() != null ? person.getNationality().getDisplayName() : null)
        .birthDate(person.getBirthDate())
        .photoUrl(person.getPhotoUrl())
        .height(person.getHeight())
        .weight(person.getWeight())
        .createdAt(person.getCreatedAt())
        .createdBy(person.getCreatedBy())
        .updatedAt(person.getUpdatedAt())
        .updatedBy(person.getUpdatedBy())
        .build();
  }

  public Person toPersonEntity(PersonDTO personDTO) {
    if (personDTO == null) {
      return null;
    }

    var builder = Person.builder()
        .id(personDTO.getId())
        .uniqueRegKey(personDTO.getUniqueRegKey())
        .firstName(personDTO.getFirstName())
        .middleName(personDTO.getMiddleName())
        .lastName(personDTO.getLastName())
        .secondLastName(personDTO.getSecondLastName())
        .displayName(personDTO.getDisplayName())
        .birthDate(personDTO.getBirthDate())
        .photoUrl(personDTO.getPhotoUrl())
        .height(personDTO.getHeight())
        .weight(personDTO.getWeight())
        .createdAt(personDTO.getCreatedAt())
        .createdBy(personDTO.getCreatedBy())
        .updatedAt(personDTO.getUpdatedAt())
        .updatedBy(personDTO.getUpdatedBy());

    if (personDTO.getBirthCountryId() != null) {
      var birthCountry = countryRepository.findById(personDTO.getBirthCountryId()).orElse(null);
      builder.birthCountry(birthCountry);
    }

    if (personDTO.getNationalityCountryId() != null) {
      var nationalityCountry = countryRepository.findById(personDTO.getNationalityCountryId()).orElse(null);
      builder.nationality(nationalityCountry);
    }
    return builder.build();
  }
}
