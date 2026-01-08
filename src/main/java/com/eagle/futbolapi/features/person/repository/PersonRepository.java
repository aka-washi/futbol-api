package com.eagle.futbolapi.features.person.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.person.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  Optional<Person> findByUniqueRegKey(String uniqueRegKey);

  Optional<Person> findByEmail(String email);

  @Query("SELECT p FROM Person p WHERE p.nationality.id = :countryId")
  Page<Person> findByCountryId(Long countryId, Pageable pageable);

  @Query("SELECT p FROM Person p WHERE p.displayName ILIKE %:searchTerm%")
  Page<Person> findByDisplayNameContainingIgnoreCase(@Param("searchTerm") String searchTerm, Pageable pageable);

  boolean existsByUniqueRegKey(String uniqueRegKey);

  boolean existsByEmail(String email);

  @Query("SELECT COUNT(p) > 0 FROM Person p WHERE p.uniqueRegKey = :uniqueRegKey AND p.id != :id")
  boolean existsByUniqueRegKeyAndNotId(@Param("uniqueRegKey") String uniqueRegKey, @Param("id") Long id);

  @Query("SELECT COUNT(p) > 0 FROM Person p WHERE p.email = :email AND p.id != :id")
  boolean existsByEmailAndNotId(@Param("email") String email, @Param("id") Long id);
}
