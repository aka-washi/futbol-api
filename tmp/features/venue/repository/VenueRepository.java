package com.eagle.futbolapi.features.venue.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.venue.entity.Venue;

@Repository
public interface VenueRepository extends BaseRepository<Venue, Long> {

  Optional<Venue> findByName(String name);

  Optional<Venue> findByDisplayName(String displayName);

  Page<Venue> findByCountryId(Long countryId, Pageable pageable);

  // Unique field methods: name + country
  @Query("SELECT v FROM Venue v WHERE v.name = :name AND v.country.id = :countryId")
  Optional<Venue> findByNameAndCountryId(
      @Param("name") String name,
      @Param("countryId") Long countryId);

  @Query("SELECT COUNT(v) > 0 FROM Venue v WHERE v.name = :name AND v.country.id = :countryId")
  boolean existsByNameAndCountryId(
      @Param("name") String name,
      @Param("countryId") Long countryId);

  @Query("SELECT COUNT(v) > 0 FROM Venue v WHERE v.name = :name AND v.country.id = :countryId AND v.id != :id")
  boolean existsByNameAndCountryIdAndIdNot(
      @Param("name") String name,
      @Param("countryId") Long countryId,
      @Param("id") Long id);

  boolean existsByName(String name);

  boolean existsByDisplayName(String displayName);

  boolean existsByCountryId(Long countryId);

  boolean existsByNameAndIdNot(String name, Long id);

  boolean existsByDisplayNameAndIdNot(String displayName, Long id);

  boolean existsByCountryIdAndIdNot(Long countryId, Long id);

}
