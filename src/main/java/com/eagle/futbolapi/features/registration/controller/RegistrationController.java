package com.eagle.futbolapi.features.registration.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.registration.dto.RegistrationDto;
import com.eagle.futbolapi.features.registration.entity.Registration;
import com.eagle.futbolapi.features.registration.mapper.RegistrationMapper;
import com.eagle.futbolapi.features.registration.service.RegistrationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/registrations")
public class RegistrationController extends BaseCrudController<Registration, RegistrationDto, RegistrationService, RegistrationMapper> {

  private static final String RESOURCE_NAME = "Registration";
  private static final String SUCCESS_MESSAGE = "Registration retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Registration already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected RegistrationController(RegistrationService service, RegistrationMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
