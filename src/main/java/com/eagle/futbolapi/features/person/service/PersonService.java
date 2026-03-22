package com.eagle.futbolapi.features.person.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.person.dto.PersonDto;
import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.person.mapper.PersonMapper;
import com.eagle.futbolapi.features.person.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@Validated
public class PersonService extends BaseCrudService<Person, Long, PersonDto> {

  private final PersonRepository repository;
  private final CountryService countryService;

  protected PersonService(PersonRepository repository, PersonMapper mapper, CountryService countryService) {
    super(repository, mapper);
    this.repository = repository;
    this.countryService = countryService;
  }

  public Optional<Person> getByUniqueRegKey(String uniqueRegKey) {
    if (uniqueRegKey == null || uniqueRegKey.trim().isEmpty()) {
      throw new IllegalArgumentException("Unique registration key cannot be null or empty");
    }
    return repository.findByUniqueRegKey(uniqueRegKey);
  }

  public Optional<Person> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Person display name cannot be null or empty");
    }
    return repository.findByDisplayName(displayName);
  }

  public Optional<Person> getByEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be null or empty");
    }
    return repository.findByEmail(email);
  }

  /**
   * Finds an existing person by uniqueRegKey or creates a new one if not found.
   * The uniqueRegKey is generated from the person data.
   *
   * @param dto The PersonDto containing the person data
   * @return The existing or newly created Person entity
   */
  public Person findOrCreate(PersonDto dto) {
    if (dto == null) {
      throw new IllegalArgumentException("PersonDto cannot be null");
    }

    log.debug("Finding or creating person from DTO");

    // Convert DTO to entity
    Person person = convertToEntity(dto);

    // Resolve relationships and generate uniqueRegKey
    resolveRelationships(dto, person);

    // Try to find existing person by uniqueRegKey
    Optional<Person> existing = getByUniqueRegKey(person.getUniqueRegKey());

    if (existing.isPresent()) {
      log.debug("Person with uniqueRegKey {} already exists, using existing person with ID {}",
          person.getUniqueRegKey(), existing.get().getId());
      return existing.get();
    }

    // Person doesn't exist, create new one
    log.debug("Person with uniqueRegKey {} does not exist, creating new person",
        person.getUniqueRegKey());

    // Save the person (skip duplicate check since we already checked by uniqueRegKey)
    Person saved = repository.save(person);
    log.info("Successfully created new person with ID {} and uniqueRegKey {}",
        saved.getId(), saved.getUniqueRegKey());

    return saved;
  }

  @Override
  protected boolean isDuplicate(@NotNull Person entity) {
    Objects.requireNonNull(entity, "Person cannot be null");

    return isDuplicate(entity, UniquenessStrategy.ANY);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Person entity) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(entity, "Person cannot be null");

    log.debug(
        "Checking for duplicates: ID={}, uniqueRegKey={}, firstName={}, lastName={}, displayName={}, email={}",
        id, entity.getUniqueRegKey(), entity.getFirstName(), entity.getLastName(),
        entity.getDisplayName(), entity.getEmail());

    boolean result = isDuplicate(id, entity, UniquenessStrategy.ANY);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

  @Override
  protected void resolveRelationships(PersonDto dto, Person person) {
    // Map birth country from display name or ID
    if (dto.getBirthCountryDisplayName() != null && !dto.getBirthCountryDisplayName().trim().isEmpty()) {
      var country = countryService.findByDisplayName(dto.getBirthCountryDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "displayName", dto.getBirthCountryDisplayName()));
      person.setBirthCountry(country);
    } else if (dto.getBirthCountryId() != null) {
      var country = countryService.getById(dto.getBirthCountryId())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "id", dto.getBirthCountryId()));
      person.setBirthCountry(country);
    }

    // Map nationality country from display name or ID
    if (dto.getNationalityCountryDisplayName() != null && !dto.getNationalityCountryDisplayName().trim().isEmpty()) {
      var country = countryService.findByDisplayName(dto.getNationalityCountryDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "displayName", dto.getNationalityCountryDisplayName()));
      person.setNationality(country);
    } else if (dto.getNationalityCountryId() != null) {
      var country = countryService.getById(dto.getNationalityCountryId())
          .orElseThrow(() -> new ResourceNotFoundException("Country", "id", dto.getNationalityCountryId()));
      person.setNationality(country);
    }

    // Generate uniqueRegKey if not already set (needed for duplicate check before persist)
    if (person.getUniqueRegKey() == null || person.getUniqueRegKey().isEmpty()) {
      person.setUniqueRegKey(person.generateUniqueRegKey());
    }
  }
}
