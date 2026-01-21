package com.eagle.futbolapi.features.seasonteam.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.seasonteam.entity.SeasonTeam;

@Repository
public interface SeasonTeamRepository extends JpaRepository<SeasonTeam, Long> {

  @Query("SELECT st FROM SeasonTeam st WHERE st.joinedDate <= :date AND (st.leftDate IS NULL OR st.leftDate >= :date)")
  Page<SeasonTeam> findActiveOnDate(@Param("date") LocalDate date, Pageable pageable);

  @Query("SELECT st FROM SeasonTeam st WHERE st.season.id = :seasonId AND st.joinedDate <= :date AND (st.leftDate IS NULL OR st.leftDate >= :date)")
  Page<SeasonTeam> findActiveTeamsBySeasonOnDate(@Param("seasonId") Long seasonId, @Param("date") LocalDate date,
      Pageable pageable);

  @Query("SELECT COUNT(st) FROM SeasonTeam st WHERE st.season.id = :seasonId")
  Long countTeamsInSeason(@Param("seasonId") Long seasonId);

}
