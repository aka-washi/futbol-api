package com.eagle.futbolapi.features.seasonTeam.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.seasonTeam.dto.SeasonTeamDto;
import com.eagle.futbolapi.features.seasonTeam.entity.SeasonTeam;
import com.eagle.futbolapi.features.seasonTeam.mapper.SeasonTeamMapper;
import com.eagle.futbolapi.features.seasonTeam.service.SeasonTeamService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for SeasonTeam resources.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/season-teams")
public class SeasonTeamController
    extends BaseCrudController<SeasonTeam, SeasonTeamDto, SeasonTeamService, SeasonTeamMapper> {

  private static final String RESOURCE_NAME = "SeasonTeam";
  private static final String SUCCESS_MESSAGE = "SeasonTeam retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "SeasonTeam already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected SeasonTeamController(SeasonTeamService service, SeasonTeamMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }
}
