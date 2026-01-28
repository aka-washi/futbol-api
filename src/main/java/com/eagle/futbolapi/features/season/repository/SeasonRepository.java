package com.eagle.futbolapi.features.season.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.season.entity.Season;

@Repository
public interface SeasonRepository extends BaseRepository<Season, Long> {

    Optional<Season> findByName(String name);

    Optional<Season> findByDisplayName(String displayName);

    Optional<Season> findByTournamentIdAndActive(Long tournamentId, Boolean active);

    @Query("SELECT s FROM Season s WHERE s.tournament.id = :tournamentId " +
            "AND s.startDate <= :date AND s.endDate >= :date")
    Optional<Season> findByTournamentAndDateRange(
            @Param("tournamentId") Long tournamentId,
            @Param("date") LocalDate date);

    // Unique field methods: tournament + name
    @Query("SELECT s FROM Season s WHERE s.tournament.id = :tournamentId AND s.name = :name")
    Optional<Season> findByTournamentIdAndName(
            @Param("tournamentId") Long tournamentId,
            @Param("name") String name);

    @Query("SELECT COUNT(s) > 0 FROM Season s WHERE s.tournament.id = :tournamentId AND s.name = :name")
    boolean existsByTournamentIdAndName(
            @Param("tournamentId") Long tournamentId,
            @Param("name") String name);

    @Query("SELECT COUNT(s) > 0 FROM Season s WHERE s.tournament.id = :tournamentId AND s.name = :name AND s.id != :id")
    boolean existsByTournamentIdAndNameAndIdNot(
            @Param("tournamentId") Long tournamentId,
            @Param("name") String name,
            @Param("id") Long id);

    Page<Season> findByTournamentId(Long tournamentId, Pageable pageable);

    @Query("SELECT s FROM Season s WHERE s.startDate <= :date AND s.endDate >= :date")
    Page<Season> findByDateRange(@Param("date") LocalDate date, Pageable pageable);

}
