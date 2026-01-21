package com.eagle.futbolapi.features.staff.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.staff.dto.StaffDTO;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.staff.mapper.StaffMapper;
import com.eagle.futbolapi.features.staff.service.StaffService;

@RestController
@RequestMapping("/staff")
@Validated
public class StaffController extends BaseCrudController<Staff, StaffDTO, StaffService, StaffMapper> {

}
