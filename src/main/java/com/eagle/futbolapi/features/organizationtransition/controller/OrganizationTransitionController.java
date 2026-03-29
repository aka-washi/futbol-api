package com.eagle.futbolapi.features.organizationtransition.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.organizationtransition.dto.OrganizationTransitionDto;
import com.eagle.futbolapi.features.organizationtransition.entity.OrganizationTransition;
import com.eagle.futbolapi.features.organizationtransition.mapper.OrganizationTransitionMapper;
import com.eagle.futbolapi.features.organizationtransition.service.OrganizationTransitionService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for OrganizationTransition resources.template.dto;
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/organization-transitions")
public class OrganizationTransitionController extends
    BaseCrudController<OrganizationTransition, OrganizationTransitionDto, OrganizationTransitionService, OrganizationTransitionMapper> {

  private static final String RESOURCE_NAME = "OrganizationTransition";
  private static final String SUCCESS_MESSAGE = "OrganizationTransition retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "OrganizationTransition already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected OrganizationTransitionController(OrganizationTransitionService service,
      OrganizationTransitionMapper mapper) {
    super(
        service,
        mapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

}
