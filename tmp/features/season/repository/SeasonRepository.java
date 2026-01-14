package com.eagle.futbolapi.features.season.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    List<Season> findByTournament(Tournament tournament);

    List<Season> findByTournamentId(Long tournamentId);

    List<Season> findByActive(Boolean active);

    Optional<Season> findByTournamentIdAndActive(Long tournamentId, Boolean active);

    @Query("SELECT s FROM Season s WHERE s.startDate <= :date AND s.endDate >= :date")
    List<Season> findByDateRange(@Param("date") LocalDate date);

    @Query("SELECT s FROM Season s WHERE s.tournament.id = :tournamentId AND s.startDate <= :date AND s.endDate >= :date")
    Optional<Season> findByTournamentAndDateRange(@Param("tournamentId") Long tournamentId,
            @Param("date") LocalDate date);

}
