package com.eagle.futbolapi.features.person.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eagle.futbolapi.features.person.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

  Optional<Person> findByUniqueRegKey(String uniqueRegKey);

  Optional<Person> findByEmail(String email);

  @Query("SELECT p FROM Person p WHERE p.country.id = :countryId")
  Page<Person> findByCountryId(Long countryId, Pageable pageable);

  @Query("SELECT p FROM Person p WHERE p.displayName ILIKE %:searchTerm%")
  Page<Person> findByDisplayNameContainingIgnoreCase(@Param("searchTerm") String searchTerm, Pageable pageable);

  boolean existsByUniqueRegKey(String uniqueRegKey);

  boolean existsByEmail(String email);

  boolean existsByUniqueRegKeyAndNotId(String uniqueRegKey, Long id);

  boolean existsByEmailAndNotId(String email, Long id);
}
