package com.eagle.futbolapi.features.group.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.group.dto.GroupDto;
import com.eagle.futbolapi.features.group.entity.Group;
import com.eagle.futbolapi.features.group.mapper.GroupMapper;
import com.eagle.futbolapi.features.group.service.GroupService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for Group entity operations.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/groups")
public class GroupController extends BaseCrudController<Group, GroupDto, GroupService, GroupMapper> {

  private static final String RESOURCE_NAME = "Group";
  private static final String SUCCESS_MESSAGE = "Group retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Group already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected GroupController(GroupService service, GroupMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }
}
