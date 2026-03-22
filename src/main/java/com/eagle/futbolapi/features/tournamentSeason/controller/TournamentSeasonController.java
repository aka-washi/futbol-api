package com.eagle.futbolapi.features.tournamentSeason.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.tournamentSeason.dto.TournamentSeasonDto;
import com.eagle.futbolapi.features.tournamentSeason.entity.TournamentSeason;
import com.eagle.futbolapi.features.tournamentSeason.mapper.TournamentSeasonMapper;
import com.eagle.futbolapi.features.tournamentSeason.service.TournamentSeasonService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for TournamentSeason resources.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/tournament-seasons")
public class TournamentSeasonController extends
    BaseCrudController<TournamentSeason, TournamentSeasonDto, TournamentSeasonService, TournamentSeasonMapper> {

  private static final String RESOURCE_NAME = "TournamentSeason";
  private static final String SUCCESS_MESSAGE = "TournamentSeason retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "TournamentSeason already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected TournamentSeasonController(TournamentSeasonService service, TournamentSeasonMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }
}
