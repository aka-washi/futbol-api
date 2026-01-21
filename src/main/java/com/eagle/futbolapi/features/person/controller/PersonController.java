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

}
