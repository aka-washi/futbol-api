package com.eagle.futbolapi.features.seasonteam.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.seasonteam.entity.SeasonTeam;
import com.eagle.futbolapi.features.team.entity.Team;

@Repository
public interface SeasonTeamRepository extends JpaRepository<SeasonTeam, Long> {

    Page<SeasonTeam> findBySeason(Season season, Pageable pageable);

    Page<SeasonTeam> findBySeasonId(Long seasonId, Pageable pageable);

    Page<SeasonTeam> findByTeam(Team team, Pageable pageable);

    Page<SeasonTeam> findByTeamId(Long teamId, Pageable pageable);

    Optional<SeasonTeam> findBySeasonAndTeam(Season season, Team team);

    Optional<SeasonTeam> findBySeasonIdAndTeamId(Long seasonId, Long teamId);

    boolean existsBySeasonIdAndTeamId(Long seasonId, Long teamId);

    @Query("SELECT st FROM SeasonTeam st WHERE st.joinedDate <= :date AND (st.leftDate IS NULL OR st.leftDate >= :date)")
    Page<SeasonTeam> findActiveOnDate(@Param("date") LocalDate date, Pageable pageable);

    @Query("SELECT st FROM SeasonTeam st WHERE st.season.id = :seasonId AND st.joinedDate <= :date AND (st.leftDate IS NULL OR st.leftDate >= :date)")
    Page<SeasonTeam> findActiveTeamsBySeasonOnDate(@Param("seasonId") Long seasonId, @Param("date") LocalDate date,
            Pageable pageable);

    @Query("SELECT COUNT(st) FROM SeasonTeam st WHERE st.season.id = :seasonId")
    Long countTeamsInSeason(@Param("seasonId") Long seasonId);

}
