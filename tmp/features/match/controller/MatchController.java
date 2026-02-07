package com.eagle.futbolapi.features.match.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.match.dto.MatchDTO;
import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.match.mapper.MatchMapper;
import com.eagle.futbolapi.features.match.service.MatchService;

@Validated
@RestController
@RequestMapping("/matches")
public class MatchController extends BaseCrudController<Match, MatchDTO, MatchService, MatchMapper> {

  private static final String RESOURCE_NAME = "Match";
  private static final String SUCCESS_MESSAGE = "Match(es) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Match already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public MatchController(MatchService matchService, MatchMapper matchMapper) {
    super(
        matchService,
        matchMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

}
