package com.eagle.futbolapi.features.competition.controller;

import java.time.LocalDate;

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
import com.eagle.futbolapi.features.base.enums.CompetitionType;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.util.ResponseUtil;
import com.eagle.futbolapi.features.competition.dto.CompetitionDTO;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.competition.mapper.CompetitionMapper;
import com.eagle.futbolapi.features.competition.service.CompetitionService;

@Validated
@RestController
@RequestMapping("/competitions")
public class CompetitionController
    extends BaseCrudController<Competition, CompetitionDTO, CompetitionService, CompetitionMapper> {

  private static final String RESOURCE_NAME = "Competition";
  private static final String SUCCESS_MESSAGE = "Competition(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Competition already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public CompetitionController(CompetitionService competitionService, CompetitionMapper competitionMapper) {
    super(
        competitionService,
        competitionMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<ApiResponse<CompetitionDTO>> getCompetitionByName(@PathVariable String name) {
    Competition competition = service.getCompetitionByName(name)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "name", name));
    CompetitionDTO competitionDTO = mapper.toDTO(competition);
    return ResponseUtil.success(competitionDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/displayName/{displayName}")
  public ResponseEntity<ApiResponse<CompetitionDTO>> getCompetitionByDisplayName(@PathVariable String displayName) {
    Competition competition = service.getCompetitionByDisplayName(displayName)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "displayName", displayName));
    CompetitionDTO competitionDTO = mapper.toDTO(competition);
    return ResponseUtil.success(competitionDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/season/{seasonId}/active")
  public ResponseEntity<ApiResponse<CompetitionDTO>> getCompetitionBySeasonIdAndActive(@PathVariable Long seasonId) {
    Competition competition = service.getCompetitionBySeasonIdAndActive(seasonId, true)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "seasonId (active)", seasonId));
    CompetitionDTO competitionDTO = mapper.toDTO(competition);
    return ResponseUtil.success(competitionDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/type/{type}/date/{date}")
  public ResponseEntity<ApiResponse<CompetitionDTO>> getByTypeAndDate(
      @PathVariable String type,
      @PathVariable String date) {
    Competition competition = service.getByTypeAndDate(
        CompetitionType.valueOf(type),
        LocalDate.parse(date))
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "type and date", type + ", " + date));
    CompetitionDTO competitionDTO = mapper.toDTO(competition);
    return ResponseUtil.success(competitionDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/unique")
  public ResponseEntity<ApiResponse<CompetitionDTO>> getByUniqueValues(
      @RequestParam String name,
      @RequestParam Long seasonId,
      @RequestParam String type,
      @RequestParam String startDate,
      @RequestParam String endDate) {
    var competition = service.getByUniqueValues(
        name,
        seasonId,
        CompetitionType.valueOf(type),
        LocalDate.parse(startDate),
        LocalDate.parse(endDate));
    CompetitionDTO competitionDTO = competition.map(mapper::toDTO).orElse(null);
    return ResponseUtil.success(competitionDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/active")
  public ResponseEntity<ApiResponse<Page<CompetitionDTO>>> getActiveCompetitions(Pageable pageable) {
    Page<Competition> activeCompetitions = service.getActiveCompetitions(pageable);
    Page<CompetitionDTO> activeCompetitionDTOs = activeCompetitions.map(mapper::toDTO);
    return ResponseUtil.success(activeCompetitionDTOs, SUCCESS_MESSAGE);
  }

  @GetMapping("/season/{seasonId}")
  public ResponseEntity<ApiResponse<Page<CompetitionDTO>>> getCompetitionsBySeasonId(@PathVariable Long seasonId) {
    Pageable pageable = ResponseUtil.createPageableWithDefaults();

    Page<Competition> competitions = service.getCompetitionsBySeasonId(seasonId, pageable);
    Page<CompetitionDTO> competitionDTOs = competitions.map(mapper::toDTO);
    return ResponseUtil.successWithPagination(competitionDTOs, SUCCESS_MESSAGE);
  }

}
