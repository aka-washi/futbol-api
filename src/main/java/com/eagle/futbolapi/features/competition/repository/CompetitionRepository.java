package com.eagle.futbolapi.features.competition.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.CompetitionType;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.season.entity.Season;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

  Optional<Competition> findByName(String name);

  Optional<Competition> findByDisplayName(String displayName);

  Optional<Competition> findBySeasonIdAndActive(Long seasonId, Boolean active);

  @Query("SELECT c FROM Competition c WHERE c.type = :type " +
      "AND c.startDate <= :date AND c.endDate >= :date")
  Optional<Competition> findByTypeAndDate(CompetitionType type, LocalDate date);

  @Query("SELECT c FROM Competition c WHERE c.name = :name " +
      "AND c.season = :season AND c.type = :type " +
      "AND c.startDate = :startDate AND c.endDate = :endDate")
  Optional<Competition> findByUniqueValues(
      @Param("name") String name,
      @Param("season") Season season,
      @Param("type") CompetitionType type,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

  @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Competition c WHERE c.name = :name" +
      " AND c.season.id = :seasonId AND c.type = :type AND c.startDate = :startDate AND c.endDate = :endDate")
  boolean existsByUniqueValues(
      @Param("name") String name,
      @Param("season") Season season,
      @Param("type") CompetitionType type,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

  @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Competition c WHERE c.name = :name" +
      " AND c.season.id = :seasonId AND c.type = :type AND c.startDate = :startDate AND c.endDate = :endDate AND c.id <> :id")
  boolean existsByUniqueValuesAndIdNot(
      @Param("name") String name,
      @Param("season") Season season,
      @Param("type") CompetitionType type,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      @Param("id") Long id);

  @Query("SELECT c FROM Competition c WHERE c.active = true")
  Page<Competition> findActiveCompetitions(Pageable pageable);

  @Query("SELECT c FROM Competition c WHERE c.season.id = :seasonId")
  Page<Competition> findBySeasonId(Long seasonId);

  @Query("SELECT c FROM Competition c WHERE c.startDate >= :starDate AND c.endDate <= :endDate")
  Page<Competition> findByDateRange(LocalDate starDate, LocalDate endDate);
}
