package com.eagle.futbolapi.features.venue.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.venue.entity.Venue;

@Repository
public interface VenueRepository extends BaseRepository<Venue, Long> {

  Optional<Venue> findByDisplayName(String displayName);

}
