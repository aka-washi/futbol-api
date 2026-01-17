package com.eagle.futbolapi.features.team.controller;

import jakarta.validation.constraints.Min;

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
import com.eagle.futbolapi.features.base.entity.Gender;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.util.ResponseUtil;
import com.eagle.futbolapi.features.team.dto.TeamDTO;
import com.eagle.futbolapi.features.team.entity.AgeCategory;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.mapper.TeamMapper;
import com.eagle.futbolapi.features.team.service.TeamService;

@Validated
@RestController
@RequestMapping("/teams")
public class TeamController extends BaseCrudController<Team, TeamDTO, TeamService, TeamMapper> {

  private static final String DEFAULT_PAGE = "0";
  private static final int MIN_DEFAULT_PAGE = 0;
  private static final String DEFAULT_PAGE_SIZE = "20";
  private static final int MIN_PAGE_SIZE = 1;
  private static final String DEFAULT_SORT_FIELD = "name";
  private static final String DEFAULT_SORT_DIRECTION = "asc";

  private static final String RESOURCE_NAME = "Team";
  private static final String SUCCESS_MESSAGE = "Team(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Team already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public TeamController(TeamService teamService, TeamMapper teamMapper) {
    super(
        teamService,
        teamMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

  @GetMapping("/displayName/{displayName}")
  public ResponseEntity<ApiResponse<TeamDTO>> getTeamByDisplayName(@PathVariable String displayName) {
    var team = service.getTeamByDisplayName(displayName)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "displayName", displayName));
    TeamDTO teamDTO = mapper.toTeamDTO(team);
    return ResponseUtil.success(teamDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/teamsByNameGenderAndCategory")
  public ResponseEntity<ApiResponse<TeamDTO>> getTeamByDisplayNameGenderAgeCategory(
      @RequestParam String displayName,
      @RequestParam String gender,
      @RequestParam String ageCategory) {

    Gender genderEnum;
    try {
      genderEnum = Gender.valueOf(gender.toUpperCase());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(ApiResponse.error("INVALID_GENDER", "Invalid gender: " + gender));
    }

    AgeCategory ageCategoryEnum;
    try {
      ageCategoryEnum = AgeCategory.valueOf(ageCategory.toUpperCase());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest()
          .body(ApiResponse.error("INVALID_AGE_CATEGORY", "Invalid age category: " + ageCategory));
    }
    var team = service.getTeamByDisplayNameAndGenderAndAgeCategory(displayName, genderEnum, ageCategoryEnum)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "displayName, gender, ageCategory",
            displayName + ", " + gender + ", " + ageCategory));
    TeamDTO teamDTO = mapper.toTeamDTO(team);
    return ResponseUtil.success(teamDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/teamsByGender/{gender}")
  public ResponseEntity<ApiResponse<Page<TeamDTO>>> getTeamsByGender(
      @PathVariable String gender,
      @RequestParam(defaultValue = DEFAULT_PAGE) @Min(MIN_DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Min(MIN_PAGE_SIZE) int size,
      @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
      @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDirection) {
    Pageable pageable = ResponseUtil.buildPageable(page, size, sortField, sortDirection);

    Gender genderEnum;
    try {
      genderEnum = Gender.valueOf(gender.toUpperCase());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(ApiResponse.error("INVALID_GENDER", "Invalid gender: " + gender));
    }
    Page<Team> teamPage = service.getTeamsByGender(genderEnum, pageable);
    Page<TeamDTO> teams = teamPage.map(mapper::toTeamDTO);
    return ResponseUtil.success(teams, SUCCESS_MESSAGE);
  }

  @GetMapping("/teamsByGenderAgeCategoryAndOrganizationId")
  public ResponseEntity<ApiResponse<Page<TeamDTO>>> getTeamsByGenderAgeCategoryAndOrganizationId(
      @RequestParam String gender,
      @RequestParam String ageCategory,
      @RequestParam Long organizationId,
      @RequestParam(defaultValue = DEFAULT_PAGE) @Min(MIN_DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Min(MIN_PAGE_SIZE) int size,
      @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
      @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDirection) {

    Pageable pageable = ResponseUtil.buildPageable(page, size, sortField, sortDirection);

    Gender genderEnum;
    try {
      genderEnum = Gender.valueOf(gender.toUpperCase());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(ApiResponse.error("INVALID_GENDER", "Invalid gender: " + gender));
    }

    AgeCategory ageCategoryEnum;
    try {
      ageCategoryEnum = AgeCategory.valueOf(ageCategory.toUpperCase());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest()
          .body(ApiResponse.error("INVALID_AGE_CATEGORY", "Invalid age category: " + ageCategory));
    }

    Page<Team> teamPage = service.getTeamsByGenderAndAgeCategoryAndOrganizationId(genderEnum, ageCategoryEnum,
        organizationId, pageable);
    Page<TeamDTO> teams = teamPage.map(mapper::toTeamDTO);
    return ResponseUtil.success(teams, SUCCESS_MESSAGE);
  }

  @GetMapping("/teamsByGenderAgeCategoryAndCountryId")
  public ResponseEntity<ApiResponse<Page<TeamDTO>>> getTeamsByGenderAgeCategoryAndCountryId(
      @RequestParam String gender,
      @RequestParam String ageCategory,
      @RequestParam Long countryId,
      @RequestParam(defaultValue = DEFAULT_PAGE) @Min(MIN_DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Min(MIN_PAGE_SIZE) int size,
      @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
      @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDirection) {
    Pageable pageable = ResponseUtil.buildPageable(page, size, sortField, sortDirection);

    Gender genderEnum;
    try {
      genderEnum = Gender.valueOf(gender.toUpperCase());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(ApiResponse.error("INVALID_GENDER", "Invalid gender: " + gender));
    }
    AgeCategory ageCategoryEnum;
    try {
      ageCategoryEnum = AgeCategory.valueOf(ageCategory.toUpperCase());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest()
          .body(ApiResponse.error("INVALID_AGE_CATEGORY", "Invalid age category: " + ageCategory));
    }
    Page<Team> teamPage = service.getTeamsByGenderAndAgeCategoryAndCountryId(genderEnum, ageCategoryEnum,
        countryId, pageable);
    Page<TeamDTO> teams = teamPage.map(mapper::toTeamDTO);
    return ResponseUtil.success(teams, SUCCESS_MESSAGE);
  }

  @Override
  protected Page<Team> getAllEntities(Pageable pageable) {
    return service.getAll(pageable);
  }

  @Override
  protected Team getEntityById(Long id) {
    return service.getById(id)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "id", id));
  }

  @Override
  protected Team createEntity(TeamDTO dto) {
    return service.create(dto);
  }

  @Override
  protected Team updateEntity(Long id, TeamDTO dto) {
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
  protected TeamDTO toDTO(Team entity) {
    return mapper.toTeamDTO(entity);
  }

  @Override
  protected Team toEntity(TeamDTO dto) {
    return mapper.toTeam(dto);
  }
}
