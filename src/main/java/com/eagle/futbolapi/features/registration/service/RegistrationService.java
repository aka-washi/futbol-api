package com.eagle.futbolapi.features.registration.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.competition.service.CompetitionService;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.player.repository.PlayerRepository;
import com.eagle.futbolapi.features.registration.dto.RegistrationDto;
import com.eagle.futbolapi.features.registration.entity.Registration;
import com.eagle.futbolapi.features.registration.mapper.RegistrationMapper;
import com.eagle.futbolapi.features.registration.repository.RegistrationRepository;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.staff.repository.StaffRepository;
import com.eagle.futbolapi.features.team.service.TeamService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@Validated
public class RegistrationService extends BaseCrudService<Registration, Long, RegistrationDto> {

  private final CompetitionService competitionService;
  private final TeamService teamService;
  private final PlayerRepository playerRepository;
  private final StaffRepository staffRepository;

  public RegistrationService(RegistrationRepository repository, RegistrationMapper mapper,
      CompetitionService competitionService, TeamService teamService,
      PlayerRepository playerRepository, StaffRepository staffRepository) {
    super(repository, mapper);
    this.competitionService = competitionService;
    this.teamService = teamService;
    this.playerRepository = playerRepository;
    this.staffRepository = staffRepository;
  }

  @Override
  protected boolean isDuplicate(@NotNull Registration registration) {
    Objects.requireNonNull(registration, "Registration cannot be null");
    return isDuplicate(registration, UniquenessStrategy.ANY);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Registration registration) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(registration, "Registration cannot be null");

    log.debug(
        "Checking duplicates for ID={}, competitionId={}, teamId={}, playerId={}, staffId={}",
        id,
        registration.getCompetition() != null ? registration.getCompetition().getId() : null,
        registration.getTeam() != null ? registration.getTeam().getId() : null,
        registration.getPlayer() != null ? registration.getPlayer().getId() : null,
        registration.getStaff() != null ? registration.getStaff().getId() : null);

    boolean result = isDuplicate(id, registration, UniquenessStrategy.ANY);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

  @Override
  protected void resolveRelationships(RegistrationDto dto, Registration entity) {
    // Competition by display name or id
    if (dto.getCompetitionDisplayName() != null && !dto.getCompetitionDisplayName().trim().isEmpty()) {
      var competition = competitionService.findByDisplayName(dto.getCompetitionDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Competition", "displayName", dto.getCompetitionDisplayName()));
      entity.setCompetition(competition);
    } else if (dto.getCompetitionId() != null) {
      var competition = competitionService.getById(dto.getCompetitionId())
          .orElseThrow(() -> new ResourceNotFoundException("Competition", "id", dto.getCompetitionId()));
      entity.setCompetition(competition);
    }

    // Team by display name or id
    if (dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty()) {
      var team = teamService.findByDisplayName(dto.getTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getTeamDisplayName()));
      entity.setTeam(team);
    } else if (dto.getTeamId() != null) {
      var team = teamService.getById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId()));
      entity.setTeam(team);
    }

    // Player by id
    if (dto.getPlayerId() != null) {
      Player player = playerRepository.findById(dto.getPlayerId())
          .orElseThrow(() -> new ResourceNotFoundException("Player", "id", dto.getPlayerId()));
      entity.setPlayer(player);
    }

    // Staff by id
    if (dto.getStaffId() != null) {
      Staff staff = staffRepository.findById(dto.getStaffId())
          .orElseThrow(() -> new ResourceNotFoundException("Staff", "id", dto.getStaffId()));
      entity.setStaff(staff);
    }
  }

}
