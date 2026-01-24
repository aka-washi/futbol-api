package com.eagle.futbolapi.features.tournament.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.dto.ApiResponse;
import com.eagle.futbolapi.features.base.enums.TournamentType;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.util.ResponseUtil;
import com.eagle.futbolapi.features.tournament.dto.TournamentDTO;
import com.eagle.futbolapi.features.tournament.entity.Tournament;
import com.eagle.futbolapi.features.tournament.mapper.TournamentMapper;
import com.eagle.futbolapi.features.tournament.service.TournamentService;

@Validated
@RestController
@RequestMapping("/tournaments")
public class TournamentController
    extends BaseCrudController<Tournament, TournamentDTO, TournamentService, TournamentMapper> {

  private static final String RESOURCE_NAME = "Tournament";
  private static final String SUCCESS_MESSAGE = "Tournament(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Tournament already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public TournamentController(TournamentService tournamentService, TournamentMapper tournamentMapper) {
    super(
        tournamentService,
        tournamentMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<ApiResponse<TournamentDTO>> getTournamentByName(@PathVariable String name) {
    Tournament tournament = service.getTournamentByName(name)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "name", name));
    TournamentDTO tournamentDTO = mapper.toDTO(tournament);
    return ResponseUtil.success(tournamentDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/displayName/{displayName}")
  public ResponseEntity<ApiResponse<TournamentDTO>> getTournamentByDisplayName(@PathVariable String displayName) {
    Tournament tournament = service.getTournamentByDisplayName(displayName)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "displayName", displayName));
    TournamentDTO tournamentDTO = mapper.toDTO(tournament);
    return ResponseUtil.success(tournamentDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/unique")
  public ResponseEntity<ApiResponse<TournamentDTO>> getTournamentByUniqueValues(
      @RequestParam Long organizationId,
      @RequestParam String type,
      @RequestParam String ageCategory,
      @RequestParam Integer level) {
    Tournament tournament = service.getTournamentByUniqueValues(organizationId, type, ageCategory, level)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "unique values",
            String.format("organizationId: %d, type: %s, ageCategory: %s, level: %d",
                organizationId, type, ageCategory, level)));
    TournamentDTO tournamentDTO = mapper.toDTO(tournament);
    return ResponseUtil.success(tournamentDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/byOrganizationAndActive")
  public ResponseEntity<ApiResponse<Page<TournamentDTO>>> getTournamentsByOrganizationAndActive(
      @RequestParam Long organizationId,
      @RequestParam Boolean active) {
    Pageable pageable = ResponseUtil.createPageableWithDefaults();

    Page<Tournament> tournaments = service.getTournamentsByOrganizationAndActive(organizationId, active, pageable);
    Page<TournamentDTO> tournamentDTOs = tournaments.map(mapper::toDTO);
    return ResponseUtil.successWithPagination(tournamentDTOs, SUCCESS_MESSAGE);
  }

  @GetMapping("/byTypeAndActive")
  public ResponseEntity<ApiResponse<Page<TournamentDTO>>> getTournamentsByTypeAndActive(
      @RequestParam TournamentType type,
      @RequestParam Boolean active) {
    Pageable pageable = ResponseUtil.createPageableWithDefaults();

    Page<Tournament> tournaments = service.getTournamentsByTypeAndActive(type, active, pageable);
    Page<TournamentDTO> tournamentDTOs = tournaments.map(mapper::toDTO);
    return ResponseUtil.successWithPagination(tournamentDTOs, SUCCESS_MESSAGE);
  }

}
