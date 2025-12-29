package com.eagle.futbolapi.features.venue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.venue.entity.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {

}
