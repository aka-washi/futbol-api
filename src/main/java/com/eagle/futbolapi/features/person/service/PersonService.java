package com.eagle.futbolapi.features.person.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.person.repository.PersonRepository;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

import jakarta.validation.constraints.NotNull;

@Service
@Transactional
public class PersonService extends BaseCrudService<Person, Long> {

  private final PersonRepository personRepository;

  public PersonService(PersonRepository personRepository) {
    super(personRepository);
    this.personRepository = personRepository;
  }

  public Optional<Person> getPersonByUniqueRegKey(String uniqueRegKey) {
    if (uniqueRegKey == null || uniqueRegKey.trim().isEmpty()) {
      throw new IllegalArgumentException("Unique Registration Key cannot be null or empty");
    }
    return personRepository.findByUniqueRegKey(uniqueRegKey);
  }

  public Optional<Person> getPersonByEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be null or empty");
    }
    return personRepository.findByEmail(email);
  }

  public Page<Person> getPersonsByCountryId(Long countryId, Pageable pageable) {
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    if (countryId == null) {
      throw new IllegalArgumentException("Country ID cannot be null");
    }
    return personRepository.findByCountryId(countryId, pageable);
  }

  public Page<Person> searchPersonsByDisplayName(String searchTerm, Pageable pageable) {
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    if (searchTerm == null || searchTerm.trim().isEmpty()) {
      throw new IllegalArgumentException("Search term cannot be null or empty");
    }
    return personRepository.findByDisplayNameContainingIgnoreCase(searchTerm, pageable);
  }

  @Override
  protected boolean isDuplicate(@NotNull Person person) {
    Objects.requireNonNull(person, "Person entity cannot be null");
    boolean uniqueRegKeyExists = personRepository.existsByUniqueRegKey(person.getUniqueRegKey());
    boolean emailExists = personRepository.existsByEmail(person.getEmail());
    return uniqueRegKeyExists || emailExists;
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull Person person) {
    Objects.requireNonNull(person, "Person entity cannot be null");
    Objects.requireNonNull(id, "Person ID cannot be null");

    if (person.getUniqueRegKey() == null || person.getEmail() == null) {
      throw new IllegalArgumentException("Unique Registration Key and Email cannot be null");
    }

    boolean uniqueRegKeyExists = personRepository.existsByUniqueRegKeyAndNotId(person.getUniqueRegKey(), id);
    boolean emailExists = personRepository.existsByEmailAndNotId(person.getEmail(), id);

    return uniqueRegKeyExists || emailExists;
  }

}
