package com.eagle.futbolapi.features.stage.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.StageStatus;
import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.stage.entity.Stage;

@Repository
public interface StageRepository extends BaseRepository<Stage, Long> {

    Optional<Stage> findByName(String name);

    Optional<Stage> findByDisplayName(String displayName);

    Optional<Stage> findByCompetitionId(Long competitionId);

    // Unique field methods: competition + order
    Optional<Stage> findByCompetitionIdAndOrder(Long competitionId, Integer order);

    boolean existsByCompetitionIdAndOrder(Long competitionId, Integer order);

    boolean existsByCompetitionIdAndOrderAndIdNot(Long competitionId, Integer order, Long id);

    @Query("SELECT s FROM Stage s WHERE s.competition.id = :competitionId " +
            "AND s.startDate <= :date AND s.endDate >= :date")
    Optional<Stage> findByStagesByCompetitionIdAndDateRange(@Param("competitionId") Long competitionId,
            @Param("date") LocalDate date);

    @Query("SELECT s FROM Stage s WHERE s.startDate <= :date AND s.endDate >= :date")
    Page<Stage> findByDateRange(@Param("date") LocalDate date, Pageable pageable);

    Page<Stage> findByCompetitionIdAndStatus(Long competitionId, StageStatus status, Pageable pageable);

}
