package com.eagle.futbolapi.features.leaguemembership.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.leaguemembership.entity.LeagueMembership;
import com.eagle.futbolapi.features.organization.entity.Organization;

@Repository
public interface LeagueMembershipRepository extends BaseRepository<LeagueMembership, Long> {

  Optional<LeagueMembership> findByLeagueAndMember(Organization league, Organization member);
  boolean existsByLeagueAndMember(Organization league, Organization member);

}
