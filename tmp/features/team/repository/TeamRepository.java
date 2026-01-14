package com.eagle.futbolapi.features.team.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.team.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByName(String name);

    Optional<Team> findByDisplayName(String displayName);

    Optional<Team> findByCode(String code);

    List<Team> findByCountry(Country country);

    List<Team> findByCountryId(Long countryId);

    List<Team> findByActive(Boolean active);

    @Query("SELECT t FROM Team t WHERE t.name ILIKE %:searchTerm% OR t.displayName ILIKE %:searchTerm%")
    List<Team> findByNameContainingIgnoreCase(@Param("searchTerm") String searchTerm);

    boolean existsByName(String name);

    boolean existsByDisplayName(String displayName);

    boolean existsByCode(String code);

}
