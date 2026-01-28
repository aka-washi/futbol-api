package com.eagle.futbolapi.features.person.service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.person.dto.PersonDTO;
import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.person.mapper.PersonMapper;
import com.eagle.futbolapi.features.person.repository.PersonRepository;

@Service
@Transactional
public class PersonService extends BaseCrudService<Person, Long, PersonDTO> {

  private final PersonRepository personRepository;
  private final CountryService countryService;

  public PersonService(PersonRepository personRepository, CountryService countryService, PersonMapper mapper) {
    super(personRepository, mapper);
    this.personRepository = personRepository;
    this.countryService = countryService;
  }

  public Optional<Person> getByUniqueRegKey(String uniqueRegKey) {
    if (uniqueRegKey == null || uniqueRegKey.trim().isEmpty()) {
      throw new IllegalArgumentException("Unique registration key cannot be null or empty");
    }
    return personRepository.findByUniqueRegKey(uniqueRegKey);
  }

  public Optional<Person> getByEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be null or empty");
    }
    return personRepository.findByEmail(email);
  }

  public Page<Person> getByCountryId(Long countryId, Pageable pageable) {
    if (countryId == null) {
      throw new IllegalArgumentException("Country ID cannot be null");
    }
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return personRepository.findByCountryId(countryId, pageable);
  }

  @Override
  protected void resolveRelationships(PersonDTO dto, Person person) {
    // Map birth country from display name or ID
    if (dto.getBirthCountryDisplayName() != null && !dto.getBirthCountryDisplayName().trim().isEmpty()) {
      var country = countryService.getCountryByDisplayName(dto.getBirthCountryDisplayName())
          .orElseThrow(
              () -> new ResourceNotFoundException("Country", "displayName", dto.getBirthCountryDisplayName()));
      person.setBirthCountry(country);
    } else if (dto.getBirthCountryId() != null) {
      var country = countryService.getById(dto.getBirthCountryId())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "id", dto.getBirthCountryId()));
      person.setBirthCountry(country);
    }

    // Map nationality country from display name or ID
    if (dto.getNationalityCountryDisplayName() != null && !dto.getNationalityCountryDisplayName().trim().isEmpty()) {
      var country = countryService.getCountryByDisplayName(dto.getNationalityCountryDisplayName())
          .orElseThrow(
              () -> new ResourceNotFoundException("Country", "displayName", dto.getNationalityCountryDisplayName()));
      person.setNationality(country);
    } else if (dto.getNationalityCountryId() != null) {
      var country = countryService.getById(dto.getNationalityCountryId())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "id", dto.getNationalityCountryId()));
      person.setNationality(country);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Person person) {
    Objects.requireNonNull(person, "Person cannot be null");

    // Check multiple unique constraints: uniqueRegKey OR email
    return (person.getUniqueRegKey() != null
        && existsByUniqueFields(Map.of("uniqueRegKey", person.getUniqueRegKey())))
        || (person.getEmail() != null && existsByUniqueFields(Map.of("email", person.getEmail())));
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Person person) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(person, "Person cannot be null");

    // Check multiple unique constraints excluding current ID: uniqueRegKey OR email
    return (person.getUniqueRegKey() != null
        && existsByUniqueFieldsAndNotId(Map.of("uniqueRegKey", person.getUniqueRegKey()), id))
        || (person.getEmail() != null && existsByUniqueFieldsAndNotId(Map.of("email", person.getEmail()), id));
  }

}
