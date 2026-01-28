package com.eagle.futbolapi.features.season.controller;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.dto.ApiResponse;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.util.ResponseUtil;
import com.eagle.futbolapi.features.season.dto.SeasonDTO;
import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.season.mapper.SeasonMapper;
import com.eagle.futbolapi.features.season.service.SeasonService;

@Validated
@RestController
@RequestMapping("/seasons")
public class SeasonController extends BaseCrudController<Season, SeasonDTO, SeasonService, SeasonMapper> {

  private static final String RESOURCE_NAME = "Season";
  private static final String SUCCESS_MESSAGE = "Season(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Season already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public SeasonController(SeasonService seasonService, SeasonMapper seasonMapper) {
    super(
        seasonService,
        seasonMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<ApiResponse<SeasonDTO>> getSeasonByName(@PathVariable String name) {
    Season season = service.getSeasonByName(name)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "name", name));
    SeasonDTO seasonDTO = mapper.toDTO(season);
    return ResponseUtil.success(seasonDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/displayName/{displayName}")
  public ResponseEntity<ApiResponse<SeasonDTO>> getSeasonByDisplayName(@PathVariable String displayName) {
    Season season = service.getSeasonByDisplayName(displayName)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "displayName", displayName));
    SeasonDTO seasonDTO = mapper.toDTO(season);
    return ResponseUtil.success(seasonDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/tournament/{tournamentId}/active/{active}")
  public ResponseEntity<ApiResponse<SeasonDTO>> getSeasonByTournamentAndActive(
      @PathVariable Long tournamentId,
      @PathVariable Boolean active) {
    Season season = service.getSeasonByTournamentAndActive(tournamentId, active)
        .orElseThrow(
            () -> new ResourceNotFoundException(RESOURCE_NAME, "tournamentId and active",
                tournamentId + ", " + active));
    SeasonDTO seasonDTO = mapper.toDTO(season);
    return ResponseUtil.success(seasonDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/tournament/{tournamentId}/date/{date}")
  public ResponseEntity<ApiResponse<SeasonDTO>> getSeasonByTournamentAndDateRange(
      @PathVariable Long tournamentId,
      @PathVariable String date) {
    Season season = service.getSeasonByTournamentAndDateRange(tournamentId, LocalDate.parse(date))
        .orElseThrow(
            () -> new ResourceNotFoundException(RESOURCE_NAME, "tournamentId and date", tournamentId + ", " + date));
    SeasonDTO seasonDTO = mapper.toDTO(season);
    return ResponseUtil.success(seasonDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/uniqueValues")
  public ResponseEntity<ApiResponse<Optional<Season>>> getByUniqueFields(
      @RequestParam Long tournamentId,
      @RequestParam String name) {
    Optional<Season> exists = service.getByUniqueFields(Map.of(
        "tournament.id", tournamentId,
        "name", name));
    return ResponseUtil.success(exists, SUCCESS_MESSAGE);
  }

}
