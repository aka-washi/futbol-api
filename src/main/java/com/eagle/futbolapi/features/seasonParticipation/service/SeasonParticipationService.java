package com.eagle.futbolapi.features.seasonParticipation.service;

import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.season.service.SeasonService;
import com.eagle.futbolapi.features.seasonParticipation.dto.SeasonParticipationDto;
import com.eagle.futbolapi.features.seasonParticipation.entity.SeasonParticipation;
import com.eagle.futbolapi.features.seasonParticipation.mapper.SeasonParticipationMapper;
import com.eagle.futbolapi.features.seasonParticipation.repository.SeasonParticipationRepository;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
@Validated
public class SeasonParticipationService
    extends BaseCrudService<SeasonParticipation, Long, SeasonParticipationDto> {

  private final SeasonService seasonService;
  private final TeamService teamService;

  public SeasonParticipationService(SeasonParticipationRepository repository,
      SeasonParticipationMapper mapper, SeasonService seasonService, TeamService teamService) {
    super(repository, mapper);
    this.seasonService = seasonService;
    this.teamService = teamService;
  }

  @Override
  protected void resolveRelationships(SeasonParticipationDto dto, SeasonParticipation entity) {
    if (dto.getSeasonId() != null) {
      entity.setSeason(seasonService.getById(dto.getSeasonId())
          .orElseThrow(() -> new ResourceNotFoundException("Season", "id", dto.getSeasonId())));
    } else if (dto.getSeasonDisplayName() != null && !dto.getSeasonDisplayName().trim().isEmpty()) {
      entity.setSeason(seasonService.findByDisplayName(dto.getSeasonDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Season", "displayName", dto.getSeasonDisplayName())));
    }

    if (dto.getTeamId() != null) {
      entity.setTeam(teamService.getById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId())));
    } else if (dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty()) {
      entity.setTeam(teamService.findByDisplayName(dto.getTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getTeamDisplayName())));
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull SeasonParticipation entity) {
    Objects.requireNonNull(entity, "SeasonParticipation cannot be null");
    if (entity.getSeason() != null && entity.getTeam() != null) {
      return existsByUniqueFields(Map.of(
          "season.id", entity.getSeason().getId(),
          "team.id", entity.getTeam().getId()));
    }
    return false;
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull SeasonParticipation entity) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(entity, "SeasonParticipation cannot be null");
    if (entity.getSeason() != null && entity.getTeam() != null) {
      return existsByUniqueFieldsAndNotId(Map.of(
          "season.id", entity.getSeason().getId(),
          "team.id", entity.getTeam().getId()), id);
    }
    return false;
  }

}
