package com.eagle.futbolapi.features.person.service;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.person.dto.PersonDTO;
import com.eagle.futbolapi.features.person.entity.Person;

@Service
@Transactional
public class PersonService extends BaseCrudService<Person, Long, PersonDTO> {

  protected PersonService(JpaRepository<Person, Long> repository, BaseMapper<Person, PersonDTO> mapper) {
    super(repository, mapper);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected boolean isDuplicate(@NotNull Person entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Person entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

}
