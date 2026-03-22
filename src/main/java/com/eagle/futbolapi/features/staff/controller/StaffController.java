package com.eagle.futbolapi.features.staff.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.staff.dto.StaffDto;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.staff.mapper.StaffMapper;
import com.eagle.futbolapi.features.staff.service.StaffService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for Staff resources.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/staff")
public class StaffController extends BaseCrudController<Staff, StaffDto, StaffService, StaffMapper> {

  private static final String RESOURCE_NAME = "Staff";
  private static final String SUCCESS_MESSAGE = "Staff retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Staff already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected StaffController(StaffService service, StaffMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
