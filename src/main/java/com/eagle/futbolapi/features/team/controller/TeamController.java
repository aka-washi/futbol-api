package com.eagle.futbolapi.features.team.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.team.dto.TeamDto;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.mapper.TeamMapper;
import com.eagle.futbolapi.features.team.service.TeamService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for Team resources.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/teams")
public class TeamController extends BaseCrudController<Team, TeamDto, TeamService, TeamMapper> {

  private static final String RESOURCE_NAME = "Team";
  private static final String SUCCESS_MESSAGE = "Team retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Team already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";


  protected TeamController(TeamService service, TeamMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
