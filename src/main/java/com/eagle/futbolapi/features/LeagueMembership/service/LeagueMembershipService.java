package com.eagle.futbolapi.features.LeagueMembership.service;

import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.LeagueMembership.dto.LeagueMembershipDto;
import com.eagle.futbolapi.features.LeagueMembership.entity.LeagueMembership;
import com.eagle.futbolapi.features.LeagueMembership.mapper.LeagueMembershipMapper;
import com.eagle.futbolapi.features.LeagueMembership.repository.LeagueMembershipRepository;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.organization.entity.Organization;

public class LeagueMembershipService extends BaseCrudService<LeagueMembership, Long, LeagueMembershipDto> {

  private final LeagueMembershipRepository repository;

  public LeagueMembershipService(LeagueMembershipRepository repository, LeagueMembershipMapper mapper) {
    super(repository, mapper);
    this.repository = repository;
  }

  public Optional<LeagueMembership> getByLeagueAndMember(Organization league, Organization member) {
    if (league == null || member == null) {
      throw new IllegalArgumentException("League and Member cannot be null");
    }
    return repository.findByLeagueAndMember(league, member);
  }

  @Override
  protected boolean isDuplicate(LeagueMembership entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, LeagueMembership entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

}
