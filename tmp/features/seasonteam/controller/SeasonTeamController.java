package com.eagle.futbolapi.features.seasonteam.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.seasonteam.dto.SeasonTeamDTO;
import com.eagle.futbolapi.features.seasonteam.entity.SeasonTeam;
import com.eagle.futbolapi.features.seasonteam.mapper.SeasonTeamMapper;
import com.eagle.futbolapi.features.seasonteam.service.SeasonTeamService;

@Validated
@RestController
@RequestMapping("/season-teams")
public class SeasonTeamController
    extends BaseCrudController<SeasonTeam, SeasonTeamDTO, SeasonTeamService, SeasonTeamMapper> {

  private static final String RESOURCE_NAME = "SeasonTeam";
  private static final String SUCCESS_MESSAGE = "Season Team(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Season Team already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public SeasonTeamController(SeasonTeamService seasonTeamService, SeasonTeamMapper seasonTeamMapper) {
    super(
        seasonTeamService,
        seasonTeamMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

}
