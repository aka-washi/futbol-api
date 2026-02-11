package com.eagle.futbolapi.features.organization.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.organization.dto.OrganizationDto;
import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.organization.mapper.OrganizationMapper;
import com.eagle.futbolapi.features.organization.service.OrganizationService;

@Validated
@RestController
@RequestMapping("/organizations")
public class OrganizationController
    extends BaseCrudController<Organization, OrganizationDto, OrganizationService, OrganizationMapper> {

  private static final String RESOURCE_NAME = "Organization";
  private static final String SUCCESS_MESSAGE = "Organization retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Organization already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected OrganizationController(OrganizationService service, OrganizationMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
