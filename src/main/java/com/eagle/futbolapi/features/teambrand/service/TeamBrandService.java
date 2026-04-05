package com.eagle.futbolapi.features.teambrand.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.team.service.TeamService;
import com.eagle.futbolapi.features.teambrand.dto.TeamBrandDto;
import com.eagle.futbolapi.features.teambrand.entity.TeamBrand;
import com.eagle.futbolapi.features.teambrand.repository.TeamBrandRepository;

@Service
@Transactional
@Validated
public class TeamBrandService extends BaseCrudService<TeamBrand, Long, TeamBrandDto> {

  private final TeamBrandRepository repository;
  private final TeamService teamService;

  protected TeamBrandService(TeamBrandRepository repository, BaseMapper<TeamBrand, TeamBrandDto> mapper, TeamService teamService) {
    super(repository, mapper);
    this.repository = repository;
    this.teamService = teamService;
  }

  public TeamBrand findByDisplayName(String displayName) {
    return repository.findByDisplayName(displayName)
        .orElseThrow(() -> new RuntimeException("TeamBrand with display name '" + displayName + "' not found"));
  }

  @Override
  protected void resolveRelationships(TeamBrandDto dto, TeamBrand entity) {
    // Map team from display name or ID
    String teamDisplayName = dto.getTeamDisplayName();
    Long teamId = dto.getTeamId();
    if (teamDisplayName != null && !teamDisplayName.trim().isEmpty()) {
      var team = teamService.findByDisplayName(teamDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", teamDisplayName));
      entity.setTeam(team);
    } else if (teamId != null) {
      var team = teamService.getById(teamId)
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", teamId));
      entity.setTeam(team);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull TeamBrand teamBrand) {
    Objects.requireNonNull(teamBrand);
    return isDuplicate(teamBrand, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull TeamBrand teamBrand) {
    Objects.requireNonNull(id);
    Objects.requireNonNull(teamBrand);
    return isDuplicate(id, teamBrand, UniquenessStrategy.ALL);
  }

}
