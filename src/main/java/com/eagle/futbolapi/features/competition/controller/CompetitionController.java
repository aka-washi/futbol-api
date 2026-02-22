package com.eagle.futbolapi.features.competition.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.competition.dto.CompetitionDto;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.competition.mapper.CompetitionMapper;
import com.eagle.futbolapi.features.competition.service.CompetitionService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for Competition resources.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/competitions")
public class CompetitionController
    extends BaseCrudController<Competition, CompetitionDto, CompetitionService, CompetitionMapper> {

  private static final String RESOURCE_NAME = "Competition";
  private static final String SUCCESS_MESSAGE = "Competition retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Competition already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected CompetitionController(CompetitionService service, CompetitionMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }
}
