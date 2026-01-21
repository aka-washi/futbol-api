package com.eagle.futbolapi.features.standing.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.standing.entity.Standing;

@Repository
public interface StandingRepository extends JpaRepository<Standing, Long> {

  @Query("SELECT s FROM Standing s WHERE s.stage.id = :stageId ORDER BY s.position ASC")
  Page<Standing> findByStageIdOrderByPosition(@Param("stageId") Long stageId, Pageable pageable);

  @Query("SELECT s FROM Standing s WHERE s.stage.id = :stageId ORDER BY s.points DESC, s.goalDifference DESC, s.goalsFor DESC")
  Page<Standing> findByStageIdOrderByPointsDesc(@Param("stageId") Long stageId, Pageable pageable);

  @Query("SELECT s FROM Standing s WHERE s.stage.id = :stageId AND s.position <= :maxPosition ORDER BY s.position ASC")
  Page<Standing> findTopPositionsByStage(@Param("stageId") Long stageId, @Param("maxPosition") Integer maxPosition,
      Pageable pageable);

}
