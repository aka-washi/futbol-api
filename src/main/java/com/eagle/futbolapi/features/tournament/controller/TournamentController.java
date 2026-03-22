package com.eagle.futbolapi.features.tournament.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.tournament.dto.TournamentDto;
import com.eagle.futbolapi.features.tournament.entity.Tournament;
import com.eagle.futbolapi.features.tournament.mapper.TournamentMapper;
import com.eagle.futbolapi.features.tournament.service.TournamentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/tournaments")
public class TournamentController extends BaseCrudController<Tournament, TournamentDto, TournamentService, TournamentMapper> {

  private static final String RESOURCE_NAME = "Tournament";
  private static final String SUCCESS_MESSAGE = "Tournament retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Tournament already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected TournamentController(TournamentService service, TournamentMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
