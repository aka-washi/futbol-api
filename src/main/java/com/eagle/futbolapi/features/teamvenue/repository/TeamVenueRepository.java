package com.eagle.futbolapi.features.teamvenue.repository;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.teamvenue.entity.TeamVenue;

@Repository
public interface TeamVenueRepository extends BaseRepository<TeamVenue, Long> {

  boolean existsByTeamIdAndVenueId(Long teamId, Long venueId);

  boolean existsByTeamIdAndVenueIdAndIdNot(Long teamId, Long venueId, Long id);

}
