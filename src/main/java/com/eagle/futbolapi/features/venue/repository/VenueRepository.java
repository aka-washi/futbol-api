package com.eagle.futbolapi.features.venue.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.venue.entity.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

  Optional<Venue> findByName(String name);

  Optional<Venue> findByDisplayName(String displayName);

  Page<Venue> findByCountryId(Long countryId, Pageable pageable);

  boolean existsByName(String name);

  boolean existsByDisplayName(String displayName);

  boolean existsByCountryId(Long countryId);

  boolean existsByNameAndIdNot(String name, Long id);

  boolean existsByDisplayNameAndIdNot(String displayName, Long id);

  boolean existsByCountryIdAndIdNot(Long countryId, Long id);

}
