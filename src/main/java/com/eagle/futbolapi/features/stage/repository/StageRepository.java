package com.eagle.futbolapi.features.stage.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.StageStatus;
import com.eagle.futbolapi.features.stage.entity.Stage;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {

  Optional<Stage> findByName(String name);

  Optional<Stage> findByDisplayName(String displayName);

  Optional<Stage> findByTournamentId(Long tournamentId);

  @Query("SELECT s FROM Stage s WHERE s.competition.id = :competitionId " +
      "AND s.startDate <= :date AND s.endDate >= :date")
  Optional<Stage> findByStagesByCompetitionIdAndDateRange(@Param("competitionId") Long competitionId,
      @Param("date") LocalDate date);

  @Query("SELECT s FROM Stage s WHERE s.startDate <= :date AND s.endDate >= :date")
  Page<Stage> findByDateRange(@Param("date") LocalDate date);

  Page<Stage> findByCompetitionIdAndStatus(Long competitionId, StageStatus status);

}
