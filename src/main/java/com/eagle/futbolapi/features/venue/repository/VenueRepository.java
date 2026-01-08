package com.eagle.futbolapi.features.venue.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.venue.entity.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    Optional<Venue> findByName(String name);

    Optional<Venue> findByDisplayName(String displayName);

    List<Venue> findByCity(String city);

    List<Venue> findByCountry(Country country);

    List<Venue> findByCountryId(Long countryId);

    List<Venue> findByActive(Boolean active);

    List<Venue> findByCountryIdAndCity(Long countryId, String city);

    @Query("SELECT v FROM Venue v WHERE v.capacity >= :minCapacity AND v.capacity <= :maxCapacity")
    List<Venue> findByCapacityRange(@Param("minCapacity") Integer minCapacity,
            @Param("maxCapacity") Integer maxCapacity);

    @Query("SELECT v FROM Venue v WHERE v.capacity >= :minCapacity")
    List<Venue> findByMinCapacity(@Param("minCapacity") Integer minCapacity);

    List<Venue> findBySurface(String surface);

}
