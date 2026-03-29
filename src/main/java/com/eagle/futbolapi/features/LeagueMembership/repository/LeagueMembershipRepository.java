package com.eagle.futbolapi.features.LeagueMembership.repository;

import java.util.Optional;

import com.eagle.futbolapi.features.LeagueMembership.entity.LeagueMembership;
import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.organization.entity.Organization;

public interface LeagueMembershipRepository extends BaseRepository<LeagueMembership, Long> {

  Optional<LeagueMembership> findByLeagueAndMember(Organization league, Organization member);
  boolean existsByLeagueAndMember(Organization league, Organization member);

}
