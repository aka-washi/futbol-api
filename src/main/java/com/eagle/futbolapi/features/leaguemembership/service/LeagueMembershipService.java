package com.eagle.futbolapi.features.leaguemembership.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.leaguemembership.dto.LeagueMembershipDto;
import com.eagle.futbolapi.features.leaguemembership.entity.LeagueMembership;
import com.eagle.futbolapi.features.leaguemembership.mapper.LeagueMembershipMapper;
import com.eagle.futbolapi.features.leaguemembership.repository.LeagueMembershipRepository;
import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.organization.service.OrganizationService;
import com.eagle.futbolapi.features.season.service.SeasonService;

@Service
@Transactional
@Validated
public class LeagueMembershipService extends BaseCrudService<LeagueMembership, Long, LeagueMembershipDto> {

  private final LeagueMembershipRepository repository;
  private final OrganizationService organizationService;
  private final SeasonService seasonService;

  public LeagueMembershipService(LeagueMembershipRepository repository, LeagueMembershipMapper mapper,
      OrganizationService organizationService, SeasonService seasonService) {
    super(repository, mapper);
    this.repository = repository;
    this.seasonService = seasonService;
    this.organizationService = organizationService;
  }

  public Optional<LeagueMembership> getByLeagueAndMember(Organization league, Organization member) {
    if (league == null || member == null) {
      throw new IllegalArgumentException("League and Member cannot be null");
    }
    return repository.findByLeagueAndMember(league, member);
  }

  @Override
  protected void resolveRelationships(LeagueMembershipDto dto, LeagueMembership leagueMembership) {
    // Resolve League by ID or display name using OrganizationService
    String leagueDisplayName = dto.getLeagueDisplayName();
    Long leagueId = dto.getLeagueId();
    if (leagueDisplayName != null && !leagueDisplayName.trim().isEmpty()) {
      var league = organizationService.findByDisplayName(leagueDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException("League", "displayName", leagueDisplayName));
      leagueMembership.setLeague(league);
    } else if (leagueId != null) {
      var league = organizationService.getById(leagueId)
          .orElseThrow(() -> new ResourceNotFoundException("League", "id", leagueId));
      leagueMembership.setLeague(league);
    }

    // Resolve Member by ID or display name using OrganizationService
    String memberDisplayName = dto.getMemberDisplayName();
    Long memberId = dto.getMemberId();
    if (memberId != null) {
      leagueMembership.setMember(organizationService.getById(memberId)
          .orElseThrow(() -> new ResourceNotFoundException("Member", "id", memberId)));
    } else if (memberDisplayName != null && !memberDisplayName.trim().isEmpty()) {
      leagueMembership.setMember(organizationService.findByDisplayName(memberDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException("Member", "displayName", memberDisplayName)));
    }

    // Resolve Start Season by ID or display name using SeasonService
    String startSeasonDisplayName = dto.getStartSeasonDisplayName();
    Long startSeasonId = dto.getStartSeasonId();
    if (startSeasonDisplayName != null && !startSeasonDisplayName.trim().isEmpty()) {
      leagueMembership.setStartSeason(seasonService.findByDisplayName(startSeasonDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException("Start Season", "displayName", startSeasonDisplayName)));
    } else if (startSeasonId != null) {
      leagueMembership.setStartSeason(seasonService.getById(startSeasonId)
          .orElseThrow(() -> new ResourceNotFoundException("Start Season", "id", startSeasonId)));
    }

    // Resolve End Season by ID or display name using SeasonService
    String endSeasonDisplayName = dto.getEndSeasonDisplayName();
    Long endSeasonId = dto.getEndSeasonId();
    if (endSeasonDisplayName != null && !endSeasonDisplayName.trim().isEmpty()) {
      leagueMembership.setEndSeason(seasonService.findByDisplayName(endSeasonDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException("End Season", "displayName", endSeasonDisplayName)));
    } else if (endSeasonId != null) {
      leagueMembership.setEndSeason(seasonService.getById(endSeasonId)
          .orElseThrow(() -> new ResourceNotFoundException("End Season", "id", endSeasonId)));
    }

  }

  @Override
  protected boolean isDuplicate(LeagueMembership leagueMembership) {
    Objects.requireNonNull(leagueMembership, "Entity cannot be null");
    return isDuplicate(leagueMembership, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, LeagueMembership leagueMembership) {
    Objects.requireNonNull(leagueMembership, "Entity cannot be null");
    return isDuplicate(leagueMembership, UniquenessStrategy.ALL);
  }

}
