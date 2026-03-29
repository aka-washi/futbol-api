package com.eagle.futbolapi.features.seasonParticipation.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.seasonParticipation.dto.SeasonParticipationDto;
import com.eagle.futbolapi.features.seasonParticipation.entity.SeasonParticipation;
import com.eagle.futbolapi.features.seasonParticipation.mapper.SeasonParticipationMapper;
import com.eagle.futbolapi.features.seasonParticipation.service.SeasonParticipationService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for SeasonTeam resources.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/season-teams")
public class SeasonParticipationController
    extends BaseCrudController<SeasonParticipation, SeasonParticipationDto, SeasonParticipationService, SeasonParticipationMapper> {

  private static final String RESOURCE_NAME = "SeasonTeam";
  private static final String SUCCESS_MESSAGE = "SeasonTeam retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "SeasonTeam already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected SeasonParticipationController(SeasonParticipationService service, SeasonParticipationMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }
}
