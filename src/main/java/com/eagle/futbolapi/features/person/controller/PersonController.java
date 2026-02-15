package com.eagle.futbolapi.features.person.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.person.dto.PersonDto;
import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.person.mapper.PersonMapper;
import com.eagle.futbolapi.features.person.service.PersonService;

@Validated
@RestController
@RequestMapping("/persons")
public class PersonController extends BaseCrudController<Person, PersonDto, PersonService, PersonMapper> {

  private static final String RESOURCE_NAME = "Person";
  private static final String SUCCESS_MESSAGE = "Person retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Person already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected PersonController(PersonService service, PersonMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
