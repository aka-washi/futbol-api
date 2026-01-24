package com.eagle.futbolapi.features.rosterentry.service;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.player.service.PlayerService;
import com.eagle.futbolapi.features.rosterentry.dto.RosterEntryDTO;
import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;
import com.eagle.futbolapi.features.rosterentry.mapper.RosterEntryMapper;
import com.eagle.futbolapi.features.rosterentry.repository.RosterEntryRepository;
import com.eagle.futbolapi.features.season.service.SeasonService;
import com.eagle.futbolapi.features.staff.service.StaffService;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class RosterEntryService extends BaseCrudService<RosterEntry, Long, RosterEntryDTO> {

  private final RosterEntryRepository rosterEntryRepository;
  private final SeasonService seasonService;
  private final TeamService teamService;
  private final PlayerService playerService;
  private final StaffService staffService;

  public RosterEntryService(RosterEntryRepository rosterEntryRepository, SeasonService seasonService,
      TeamService teamService, PlayerService playerService, StaffService staffService, RosterEntryMapper mapper) {
    super(rosterEntryRepository, mapper);
    this.rosterEntryRepository = rosterEntryRepository;
    this.seasonService = seasonService;
    this.teamService = teamService;
    this.playerService = playerService;
    this.staffService = staffService;
  }

  public Page<RosterEntry> getActivePlayersBySeasonAndTeam(Long seasonId, Long teamId, Pageable pageable) {
    if (seasonId == null) {
      throw new IllegalArgumentException("Season ID cannot be null");
    }
    if (teamId == null) {
      throw new IllegalArgumentException("Team ID cannot be null");
    }
    Pageable effectivePageable = pageable != null ? pageable : Pageable.unpaged();
    return rosterEntryRepository.findActivePlayersBySeasonAndTeam(seasonId, teamId, effectivePageable);
  }

  public Page<RosterEntry> getActiveStaffBySeasonAndTeam(Long seasonId, Long teamId, Pageable pageable) {
    if (seasonId == null) {
      throw new IllegalArgumentException("Season ID cannot be null");
    }
    if (teamId == null) {
      throw new IllegalArgumentException("Team ID cannot be null");
    }
    Pageable effectivePageable = pageable != null ? pageable : Pageable.unpaged();
    return rosterEntryRepository.findActiveStaffBySeasonAndTeam(seasonId, teamId, effectivePageable);
  }

  public Page<RosterEntry> getActiveOnDate(LocalDate date, Pageable pageable) {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null");
    }
    Pageable effectivePageable = pageable != null ? pageable : Pageable.unpaged();
    return rosterEntryRepository.findActiveOnDate(date, effectivePageable);
  }

  @Override
  protected void resolveRelationships(RosterEntryDTO dto, RosterEntry rosterEntry) {
    // Map season from ID
    if (dto.getSeasonId() != null) {
      var season = seasonService.getById(dto.getSeasonId())
          .orElseThrow(() -> new ResourceNotFoundException("Season", "id", dto.getSeasonId()));
      rosterEntry.setSeason(season);
    }

    // Map team from display name or ID
    if (dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty()) {
      var team = teamService.getByDisplayName(dto.getTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getTeamDisplayName()));
      rosterEntry.setTeam(team);
    } else if (dto.getTeamId() != null) {
      var team = teamService.getById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId()));
      rosterEntry.setTeam(team);
    }

    // Map player from ID (optional)
    if (dto.getPlayerId() != null) {
      var player = playerService.getById(dto.getPlayerId())
          .orElseThrow(() -> new ResourceNotFoundException("Player", "id", dto.getPlayerId()));
      rosterEntry.setPlayer(player);
    }

    // Map staff from ID (optional)
    if (dto.getStaffId() != null) {
      var staff = staffService.getById(dto.getStaffId())
          .orElseThrow(() -> new ResourceNotFoundException("Staff", "id", dto.getStaffId()));
      rosterEntry.setStaff(staff);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull RosterEntry rosterEntry) {
    Objects.requireNonNull(rosterEntry, "RosterEntry cannot be null");
    // A roster entry is unique by season, team, and either player or staff
    if (rosterEntry.getSeason() != null && rosterEntry.getTeam() != null && rosterEntry.getPlayer() != null) {
      return rosterEntryRepository.existsBySeasonIdAndTeamIdAndPlayerId(
          rosterEntry.getSeason().getId(), rosterEntry.getTeam().getId(), rosterEntry.getPlayer().getId());
    }
    if (rosterEntry.getSeason() != null && rosterEntry.getTeam() != null && rosterEntry.getStaff() != null) {
      return rosterEntryRepository.existsBySeasonIdAndTeamIdAndStaffId(
          rosterEntry.getSeason().getId(), rosterEntry.getTeam().getId(), rosterEntry.getStaff().getId());
    }
    return false;
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull RosterEntry rosterEntry) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(rosterEntry, "RosterEntry cannot be null");

    if (rosterEntry.getSeason() != null && rosterEntry.getTeam() != null && rosterEntry.getPlayer() != null) {
      return rosterEntryRepository.existsBySeasonIdAndTeamIdAndPlayerIdAndIdNot(
          rosterEntry.getSeason().getId(), rosterEntry.getTeam().getId(), rosterEntry.getPlayer().getId(), id);
    }
    if (rosterEntry.getSeason() != null && rosterEntry.getTeam() != null && rosterEntry.getStaff() != null) {
      return rosterEntryRepository.existsBySeasonIdAndTeamIdAndStaffIdAndIdNot(
          rosterEntry.getSeason().getId(), rosterEntry.getTeam().getId(), rosterEntry.getStaff().getId(), id);
    }
    return false;
  }

}
