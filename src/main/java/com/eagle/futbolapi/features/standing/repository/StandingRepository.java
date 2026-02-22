package com.eagle.futbolapi.features.standing.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.standing.entity.Standing;

@Repository
public interface StandingRepository extends BaseRepository<Standing, Long> {

  Page<Standing> findByStageIdOrderByPositionAsc(Long stageId, Pageable pageable);

  @Query("SELECT s FROM Standing s WHERE s.stage.id = :stageId ORDER BY s.points DESC, s.goalDifference DESC, s.goalsFor DESC")
  Page<Standing> findByStageIdOrderByPointsDesc(@Param("stageId") Long stageId, Pageable pageable);

  @Query("SELECT s FROM Standing s WHERE s.stage.id = :stageId AND s.position <= :maxPosition ORDER BY s.position ASC")
  Page<Standing> findTopPositionsByStage(@Param("stageId") Long stageId, @Param("maxPosition") Integer maxPosition,
      Pageable pageable);

  // Unique field methods: stage + team
  Optional<Standing> findByStageIdAndTeamId(Long stageId, Long teamId);

  boolean existsByStageIdAndTeamId(Long stageId, Long teamId);

  boolean existsByStageIdAndTeamIdAndIdNot(Long stageId, Long teamId, Long id);

}
