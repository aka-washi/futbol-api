package com.eagle.futbolapi.features.staff.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.staff.dto.StaffDTO;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.staff.mapper.StaffMapper;
import com.eagle.futbolapi.features.staff.service.StaffService;

@Validated
@RestController
@RequestMapping("/staff")
public class StaffController extends BaseCrudController<Staff, StaffDTO, StaffService, StaffMapper> {

  private static final String RESOURCE_NAME = "Staff";
  private static final String SUCCESS_MESSAGE = "Staff member(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Staff member already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public StaffController(StaffService staffService, StaffMapper staffMapper) {
    super(
        staffService,
        staffMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

}
