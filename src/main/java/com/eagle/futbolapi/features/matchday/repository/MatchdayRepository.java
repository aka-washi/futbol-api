package com.eagle.futbolapi.features.matchday.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.matchday.entity.Matchday;
import com.eagle.futbolapi.features.matchday.entity.MatchdayStatus;
import com.eagle.futbolapi.features.stage.entity.Stage;

@Repository
public interface MatchdayRepository extends JpaRepository<Matchday, Long> {

    List<Matchday> findByStage(Stage stage);

    List<Matchday> findByStageId(Long stageId);

    Optional<Matchday> findByStageAndNumber(Stage stage, Integer number);

    Optional<Matchday> findByStageIdAndNumber(Long stageId, Integer number);

    Optional<Matchday> findByName(String name);

    Optional<Matchday> findByDisplayName(String displayName);

    List<Matchday> findByStatus(MatchdayStatus status);

    @Query("SELECT m FROM Matchday m WHERE m.stage.id = :stageId ORDER BY m.number ASC")
    List<Matchday> findByStageIdOrderByNumber(@Param("stageId") Long stageId);

    @Query("SELECT m FROM Matchday m WHERE m.startDate <= :date AND m.endDate >= :date")
    List<Matchday> findByDateRange(@Param("date") LocalDate date);

    List<Matchday> findByStageIdAndStatus(Long stageId, MatchdayStatus status);

}
