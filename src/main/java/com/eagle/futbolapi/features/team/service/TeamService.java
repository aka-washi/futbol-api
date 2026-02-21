package com.eagle.futbolapi.features.team.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.team.dto.TeamDto;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.mapper.TeamMapper;
import com.eagle.futbolapi.features.team.repository.TeamRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for Team entity business logic.
 */
@Slf4j
@Service
@Transactional
public class TeamService extends BaseCrudService<Team, Long, TeamDto> {


  protected TeamService(TeamRepository repository, TeamMapper mapper) {
    super(repository, mapper);
  }

  public Optional<Team> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Team display name cannot be null or empty");
    }
    return ((TeamRepository) repository).findByDisplayName(displayName);
  }


  @Override
  protected boolean isDuplicate(@NotNull Team team) {
    Objects.requireNonNull(team, "Team cannot be null");

    return isDuplicate(team, UniquenessStrategy.ANY);
  }


  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Team team) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(team, "Team cannot be null");

    return isDuplicate(id, team, UniquenessStrategy.ANY);
  }

}
