package com.eagle.futbolapi.features.LeagueMembership.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.LeagueMembership.dto.LeagueMembershipDto;
import com.eagle.futbolapi.features.LeagueMembership.entity.LeagueMembership;
import com.eagle.futbolapi.features.LeagueMembership.mapper.LeagueMembershipMapper;
import com.eagle.futbolapi.features.LeagueMembership.repository.LeagueMembershipRepository;
import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.organization.service.OrganizationService;

public class LeagueMembershipService extends BaseCrudService<LeagueMembership, Long, LeagueMembershipDto> {

  private final LeagueMembershipRepository repository;
  private final OrganizationService organizationService;

  public LeagueMembershipService(LeagueMembershipRepository repository, LeagueMembershipMapper mapper,
      OrganizationService organizationService) {
    super(repository, mapper);
    this.repository = repository;
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
