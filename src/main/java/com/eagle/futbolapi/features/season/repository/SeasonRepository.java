package com.eagle.futbolapi.features.season.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.tournament.entity.Tournament;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {

    Optional<Season> findByName(String name);

    Optional<Season> findByDisplayName(String displayName);

    Optional<Season> findByTournamentIdAndActive(Long tournamentId, Boolean active);

    @Query("SELECT s FROM Season s WHERE s.tournament.id = :tournamentId " +
            "AND s.startDate <= :date AND s.endDate >= :date")
    Optional<Season> findByTournamentAndDateRange(
            @Param("tournamentId") Long tournamentId,
            @Param("date") LocalDate date);

    @Query("SELECT s FROM Season s WHERE s.tournament = :tournament " +
            "AND s.name = :name AND s.startDate = :startDate AND s.endDate = :endDate AND s.active = :active")
    Optional<Season> findByUniqueValues(
            Tournament tournament,
            String name,
            LocalDate startDate,
            LocalDate endDate,
            Boolean active);

    Page<Season> findByTournamentId(Long tournamentId, Pageable pageable);

    @Query("SELECT s FROM Season s WHERE s.startDate <= :date AND s.endDate >= :date")
    Page<Season> findByDateRange(@Param("date") LocalDate date, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Season s WHERE s.name = :name" +
            " AND s.tournament = :tournament AND s.startDate = :startDate AND s.endDate = :endDate AND s.active = :active")
    boolean existsByUniqueValues(
            @Param("name") String name,
            @Param("tournament") Tournament tournament,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("active") boolean active);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Season s WHERE s.name = :name" +
            " AND s.tournament = :tournament AND s.startDate = :startDate AND s.endDate = :endDate AND s.active = :active AND s.id <> :id")
    boolean existsByUniqueValuesAndIdNot(
            @Param("name") String name,
            @Param("tournament") Tournament tournament,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("active") boolean active,
            @Param("id") Long id);
}
