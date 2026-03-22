package com.eagle.futbolapi.features.lineupMember.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.lineup.entity.Lineup;
import com.eagle.futbolapi.features.lineup.repository.LineupRepository;
import com.eagle.futbolapi.features.lineupMember.dto.LineupMemberDto;
import com.eagle.futbolapi.features.lineupMember.entity.LineupMember;
import com.eagle.futbolapi.features.lineupMember.mapper.LineupMemberMapper;
import com.eagle.futbolapi.features.lineupMember.repository.LineupMemberRepository;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.player.repository.PlayerRepository;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.staff.repository.StaffRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for LineupMember entity operations.
 */
@Slf4j
@Service
@Transactional
@Validated
public class LineupMemberService extends BaseCrudService<LineupMember, Long, LineupMemberDto> {

  private final LineupRepository lineupRepository;
  private final PlayerRepository playerRepository;
  private final StaffRepository staffRepository;

  public LineupMemberService(
      LineupMemberRepository repository,
      LineupMemberMapper mapper,
      LineupRepository lineupRepository,
      PlayerRepository playerRepository,
      StaffRepository staffRepository) {
    super(repository, mapper);
    this.lineupRepository = lineupRepository;
    this.playerRepository = playerRepository;
    this.staffRepository = staffRepository;
  }

  @Override
  protected void resolveRelationships(@NotNull LineupMemberDto dto, @NotNull LineupMember entity) {
    if (dto.getLineupId() != null) {
      Lineup lineup = lineupRepository.findById(dto.getLineupId())
          .orElseThrow(() -> new IllegalArgumentException(
              "Lineup not found with id: " + dto.getLineupId()));
      entity.setLineup(lineup);
    }

    if (dto.getPlayerId() != null) {
      Player player = playerRepository.findById(dto.getPlayerId())
          .orElseThrow(() -> new IllegalArgumentException(
              "Player not found with id: " + dto.getPlayerId()));
      entity.setPlayer(player);
    }

    if (dto.getStaffId() != null) {
      Staff staff = staffRepository.findById(dto.getStaffId())
          .orElseThrow(() -> new IllegalArgumentException(
              "Staff not found with id: " + dto.getStaffId()));
      entity.setStaff(staff);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull LineupMember lineupMember) {
    Objects.requireNonNull(lineupMember, "LineupMember cannot be null");
    return isDuplicate(lineupMember, UniquenessStrategy.ANY);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull LineupMember lineupMember) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(lineupMember, "LineupMember cannot be null");
    return isDuplicate(id, lineupMember, UniquenessStrategy.ANY);
  }
}
