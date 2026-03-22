package com.eagle.futbolapi.features.seasonteam.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.season.service.SeasonService;
import com.eagle.futbolapi.features.seasonteam.dto.SeasonTeamDTO;
import com.eagle.futbolapi.features.seasonteam.entity.SeasonTeam;
import com.eagle.futbolapi.features.seasonteam.mapper.SeasonTeamMapper;
import com.eagle.futbolapi.features.seasonteam.repository.SeasonTeamRepository;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class SeasonTeamService extends BaseCrudService<SeasonTeam, Long, SeasonTeamDTO> {

  private final SeasonTeamRepository seasonTeamRepository;
  private final SeasonService seasonService;
  private final TeamService teamService;

  public SeasonTeamService(SeasonTeamRepository seasonTeamRepository, SeasonService seasonService,
      TeamService teamService, SeasonTeamMapper mapper) {
    super(seasonTeamRepository, mapper);
    this.seasonTeamRepository = seasonTeamRepository;
    this.seasonService = seasonService;
    this.teamService = teamService;
  }

  public Page<SeasonTeam> getActiveOnDate(LocalDate date, Pageable pageable) {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null");
    }
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return seasonTeamRepository.findActiveOnDate(date, pageable);
  }

  public Page<SeasonTeam> getActiveTeamsBySeasonOnDate(Long seasonId, LocalDate date, Pageable pageable) {
    if (seasonId == null) {
      throw new IllegalArgumentException("Season ID cannot be null");
    }
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null");
    }
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return seasonTeamRepository.findActiveTeamsBySeasonOnDate(seasonId, date, pageable);
  }

  @Override
  protected void resolveRelationships(SeasonTeamDTO dto, SeasonTeam seasonTeam) {
    // Map season from ID
    if (dto.getSeasonId() != null) {
      var season = seasonService.getById(dto.getSeasonId())
          .orElseThrow(() -> new ResourceNotFoundException("Season", "id", dto.getSeasonId()));
      seasonTeam.setSeason(season);
    }

    // Map team from display name or ID
    if (dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty()) {
      var team = teamService.getByDisplayName(dto.getTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getTeamDisplayName()));
      seasonTeam.setTeam(team);
    } else if (dto.getTeamId() != null) {
      var team = teamService.getById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId()));
      seasonTeam.setTeam(team);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull SeasonTeam seasonTeam) {
    Objects.requireNonNull(seasonTeam, "SeasonTeam cannot be null");

    // Check composite unique constraint: season + team
    if (seasonTeam.getSeason() != null && seasonTeam.getTeam() != null) {
      return existsByUniqueFields(Map.of(
          "season.id", seasonTeam.getSeason().getId(),
          "team.id", seasonTeam.getTeam().getId()));
    }
    return false;
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull SeasonTeam seasonTeam) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(seasonTeam, "SeasonTeam cannot be null");

    // Check composite unique constraint: season + team (excluding current ID)
    if (seasonTeam.getSeason() != null && seasonTeam.getTeam() != null) {
      return existsByUniqueFieldsAndNotId(Map.of(
          "season.id", seasonTeam.getSeason().getId(),
          "team.id", seasonTeam.getTeam().getId()), id);
    }
    return false;
  }

}
