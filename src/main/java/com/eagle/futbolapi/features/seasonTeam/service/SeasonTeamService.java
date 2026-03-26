package com.eagle.futbolapi.features.seasonTeam.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.enums.Gender;
import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.season.service.SeasonService;
import com.eagle.futbolapi.features.seasonTeam.dto.SeasonTeamDto;
import com.eagle.futbolapi.features.seasonTeam.entity.SeasonTeam;
import com.eagle.futbolapi.features.seasonTeam.mapper.SeasonTeamMapper;
import com.eagle.futbolapi.features.seasonTeam.repository.SeasonTeamRepository;
import com.eagle.futbolapi.features.team.service.TeamService;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for SeasonTeam entity business logic.
 */
@Slf4j
@Service
@Transactional
@Validated
public class SeasonTeamService extends BaseCrudService<SeasonTeam, Long, SeasonTeamDto> {

  private final SeasonService seasonService;
  private final TeamService teamService;

  protected SeasonTeamService(SeasonTeamRepository repository, SeasonTeamMapper mapper,
      SeasonService seasonService, TeamService teamService) {
    super(repository, mapper);
    this.seasonService = seasonService;
    this.teamService = teamService;
  }

  @Override
  protected boolean isDuplicate(@NotNull SeasonTeam seasonTeam) {
    Objects.requireNonNull(seasonTeam, "SeasonTeam cannot be null");
    return isDuplicate(seasonTeam, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull SeasonTeam seasonTeam) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(seasonTeam, "SeasonTeam cannot be null");

    log.debug(
        "Checking for duplicates: ID={}, seasonId={}, teamId={}",
        id,
        seasonTeam.getSeason() != null ? seasonTeam.getSeason().getId() : null,
        seasonTeam.getTeam() != null ? seasonTeam.getTeam().getId() : null);

    boolean result = isDuplicate(id, seasonTeam, UniquenessStrategy.ALL);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

  @Override
  protected void resolveRelationships(SeasonTeamDto dto, SeasonTeam seasonTeam) {
    // Map season from display name or ID
    if (dto.getSeasonDisplayName() != null && !dto.getSeasonDisplayName().trim().isEmpty()) {
      var season = seasonService.findByDisplayName(dto.getSeasonDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Season", "displayName", dto.getSeasonDisplayName()));
      seasonTeam.setSeason(season);
    } else if (dto.getSeasonId() != null) {
      var season = seasonService.getById(dto.getSeasonId())
          .orElseThrow(() -> new ResourceNotFoundException("Season", "id", dto.getSeasonId()));
      seasonTeam.setSeason(season);
    }

    // Map team: prefer ID (unique). Otherwise require displayName + gender.
    boolean hasTeamId = dto.getTeamId() != null;
    boolean hasTeamDisplay = dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty();
    boolean hasTeamGender = dto.getTeamGender() != null && !dto.getTeamGender().trim().isEmpty();

    if (hasTeamId) {
      var team = teamService.getById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId()));
      seasonTeam.setTeam(team);

    } else if (hasTeamDisplay && hasTeamGender) {
      Gender gender;
      try {
        gender = Gender.fromLabel(dto.getTeamGender());
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
            "Invalid teamGender value: '" + dto.getTeamGender() + "'. Allowed: MALE, FEMALE");
      }
      var team = teamService.findByDisplayNameAndGender(dto.getTeamDisplayName(), gender)
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName and gender",
              dto.getTeamDisplayName() + "/" + dto.getTeamGender()));
      seasonTeam.setTeam(team);

    } else if (hasTeamDisplay && !hasTeamGender) {
      throw new IllegalArgumentException("When resolving team by displayName, teamGender is required");
    }
  }
}
