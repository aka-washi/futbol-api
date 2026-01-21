package com.eagle.futbolapi.features.competition.controller;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.dto.ApiResponse;
import com.eagle.futbolapi.features.base.enums.CompetitionType;
import com.eagle.futbolapi.features.base.util.ResponseUtil;
import com.eagle.futbolapi.features.competition.dto.CompetitionDTO;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.competition.mapper.CompetitionMapper;
import com.eagle.futbolapi.features.competition.service.CompetitionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/competitions")
@Validated
public class CompetitionController
    extends BaseCrudController<Competition, CompetitionDTO, CompetitionService, CompetitionMapper> {

  private static final String RESOURCE_NAME = "Competition";
  private static final String SUCCESS_MESSAGE = "Competition retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Competition already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected CompetitionController(CompetitionService service, CompetitionMapper mapper) {
    super(
        service,
        mapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<ApiResponse<CompetitionDTO>> getCompetitionByName(@RequestParam String name) {
    Competition competition = service.getCompetitionByName(name)
        .orElseThrow(() -> new RuntimeException("Competition not found with name: " + name));
    CompetitionDTO competitionDTO = mapper.toCompetitionDTO(competition);
    return ResponseUtil.success(competitionDTO, successMessage);
  }

  @GetMapping("/displayName/{displayName}")
  public ResponseEntity<ApiResponse<CompetitionDTO>> getCompetitionByDisplayName(@RequestParam String displayName) {
    Competition competition = service.getCompetitionByDisplayName(displayName)
        .orElseThrow(() -> new RuntimeException("Competition not found with display name: " + displayName));
    CompetitionDTO competitionDTO = mapper.toCompetitionDTO(competition);
    return ResponseUtil.success(competitionDTO, successMessage);
  }

  // TODO: Rename endpoint?
  @GetMapping("/season/{seasonId}/active")
  public ResponseEntity<ApiResponse<CompetitionDTO>> getCompetitionBySeasonIdAndActive(@RequestParam Long seasonId) {
    Competition competition = service.getCompetitionBySeasonIdAndActive(seasonId, true)
        .orElseThrow(() -> new RuntimeException("Active Competition not found for season id: " + seasonId));
    CompetitionDTO competitionDTO = mapper.toCompetitionDTO(competition);
    return ResponseUtil.success(competitionDTO, successMessage);
  }

  @GetMapping("/type/{type}/date/{date}")
  public ResponseEntity<ApiResponse<CompetitionDTO>> getByTypeAndDate(
      @RequestParam String type,
      @RequestParam String date) {
    Competition competition = service.getByTypeAndDate(
        CompetitionType.valueOf(type),
        LocalDate.parse(date))
        .orElseThrow(() -> new RuntimeException("Competition not found with type: " + type + " and date: " + date));
    CompetitionDTO competitionDTO = mapper.toCompetitionDTO(competition);
    return ResponseUtil.success(competitionDTO, successMessage);
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
    CompetitionDTO competitionDTO = competition.map(mapper::toCompetitionDTO).orElse(null);
    return ResponseUtil.success(competitionDTO, successMessage);
  }

  @GetMapping("/active")
  public ResponseEntity<ApiResponse<Page<CompetitionDTO>>> getActiveCompetitions(Pageable pageable) {
    // TODO: test the Pageable functionality through API, if not working change to
    // default values for page and size
    Page<Competition> activeCompetitions = service.getActiveCompetitions(pageable);
    Page<CompetitionDTO> activeCompetitionDTOs = activeCompetitions.map(mapper::toCompetitionDTO);
    return ResponseUtil.success(activeCompetitionDTOs, successMessage);
  }

  @GetMapping("/season/{seasonId}")
  public ResponseEntity<ApiResponse<Page<CompetitionDTO>>> getCompetitionsBySeasonId(@RequestParam Long seasonId) {

    Pageable pageable = ResponseUtil.createPageableWithDefaults();

    Page<Competition> competitions = service.getCompetitionsBySeasonId(seasonId, pageable);
    Page<CompetitionDTO> competitionDTOs = competitions.map(mapper::toCompetitionDTO);
    return ResponseUtil.successWithPagination(competitionDTOs, successMessage);
  }

  @Override
  protected Page<Competition> getAllEntities(Pageable pageable) {
    return service.getAll(pageable);
  }

  @Override
  protected Competition getEntityById(Long id) {
    return service.getById(id)
        .orElseThrow(() -> new RuntimeException("Competition not found with id: " + id));
  }

  @Override
  protected Competition createEntity(CompetitionDTO dto) {
    return service.create(dto);
  }

  @Override
  protected Competition updateEntity(Long id, CompetitionDTO dto) {
    return service.update(id, dto);
  }

  @Override
  protected void deleteEntity(Long id) {
    service.delete(id);
  }

  @Override
  protected boolean existsById(Long id) {
    return service.existsById(id);
  }

  @Override
  protected CompetitionDTO toDTO(Competition entity) {
    return mapper.toCompetitionDTO(entity);
  }

  @Override
  protected Competition toEntity(CompetitionDTO dto) {
    return mapper.toCompetition(dto);
  }
}
