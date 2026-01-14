package com.eagle.futbolapi.features.stage.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.stage.entity.Stage;
import com.eagle.futbolapi.features.stage.entity.StageStatus;
import com.eagle.futbolapi.features.structure.entity.Structure;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {

    Page<Stage> findByCompetition(Competition competition, Pageable pageable);

    Page<Stage> findByCompetitionId(Long competitionId, Pageable pageable);

    Page<Stage> findByStructure(Structure structure, Pageable pageable);

    Page<Stage> findByStructureId(Long structureId, Pageable pageable);

    Optional<Stage> findByName(String name);

    Optional<Stage> findByDisplayName(String displayName);

    Page<Stage> findByStatus(StageStatus status, Pageable pageable);

    @Query("SELECT s FROM Stage s WHERE s.competition.id = :competitionId ORDER BY s.order ASC")
    Page<Stage> findByCompetitionIdOrderByOrder(@Param("competitionId") Long competitionId, Pageable pageable);

    Optional<Stage> findByCompetitionIdAndOrder(Long competitionId, Integer order);

    @Query("SELECT s FROM Stage s WHERE s.startDate <= :date AND s.endDate >= :date")
    Page<Stage> findByDateRange(@Param("date") LocalDate date, Pageable pageable);

    Page<Stage> findByCompetitionIdAndStatus(Long competitionId, StageStatus status, Pageable pageable);

}
