package com.eagle.futbolapi.features.person.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.person.repository.PersonRepository;
import com.eagle.futbolapi.features.base.service.BaseCrudService;

@Service
@Transactional
public class PersonService extends BaseCrudService<Person, Long> {

}
