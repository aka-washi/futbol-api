package com.eagle.futbolapi.features.person.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.person.entity.Person;

@Repository
public interface PersonRepository extends BaseRepository<Person, Long> {

  Optional<Person> findByUniqueRegKey(String uniqueRegKey);

  Optional<Person> findByDisplayName(String displayName);

  Optional<Person> findByEmail(String email);
  
}
