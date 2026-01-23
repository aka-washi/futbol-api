package com.eagle.futbolapi.features.person.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.person.dto.PersonDTO;
import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.person.mapper.PersonMapper;
import com.eagle.futbolapi.features.person.service.PersonService;

@RestController
@RequestMapping("/persons")
@Validated
public class PersonController extends BaseCrudController<Person, PersonDTO, PersonService, PersonMapper> {

  protected PersonController(PersonService service, PersonMapper mapper, String resourceName, String successMessage,
      String duplicateMessage, String serverError) {
    super(service, mapper, resourceName, successMessage, duplicateMessage, serverError);
    // TODO Auto-generated constructor stub
  }

}
