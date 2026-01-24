package com.eagle.futbolapi.features.lineup.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.lineup.dto.LineupDTO;
import com.eagle.futbolapi.features.lineup.entity.Lineup;
import com.eagle.futbolapi.features.lineup.mapper.LineupMapper;
import com.eagle.futbolapi.features.lineup.service.LineupService;

@Validated
@RestController
@RequestMapping("/lineups")
public class LineupController extends BaseCrudController<Lineup, LineupDTO, LineupService, LineupMapper> {

  private static final String RESOURCE_NAME = "Lineup";
  private static final String SUCCESS_MESSAGE = "Lineup(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Lineup already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public LineupController(LineupService lineupService, LineupMapper lineupMapper) {
    super(
        lineupService,
        lineupMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

}
