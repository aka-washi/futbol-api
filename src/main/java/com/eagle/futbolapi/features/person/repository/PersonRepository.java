package com.eagle.futbolapi.features.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.person.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
