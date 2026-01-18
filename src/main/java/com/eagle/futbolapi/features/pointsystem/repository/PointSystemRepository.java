package com.eagle.futbolapi.features.pointsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;

@Repository
public interface PointSystemRepository extends JpaRepository<PointSystem, Long> {

  Optional<PointSystem> findByName(String name);

  Optional<PointSystem> findByDisplayName(String displayName);

  boolean existsByName(String name);

  boolean existsByDisplayName(String displayName);

  /**
   * Finds a PointSystem with the same point values as defined in the equals
   * method
   */
  @Query("SELECT p FROM PointSystem p WHERE p.pointsForWin = :pointsForWin " +
      "AND p.pointsForDraw = :pointsForDraw " +
      "AND p.pointsForLoss = :pointsForLoss " +
      "AND ((:pointsForWinOnPenalties IS NULL AND p.pointsForWinOnPenalties IS NULL) " +
      "OR p.pointsForWinOnPenalties = :pointsForWinOnPenalties) " +
      "AND ((:pointsForLossOnPenalties IS NULL AND p.pointsForLossOnPenalties IS NULL) " +
      "OR p.pointsForLossOnPenalties = :pointsForLossOnPenalties)")
  Optional<PointSystem> findByPointValues(
      @Param("pointsForWin") Integer pointsForWin,
      @Param("pointsForDraw") Integer pointsForDraw,
      @Param("pointsForLoss") Integer pointsForLoss,
      @Param("pointsForWinOnPenalties") Integer pointsForWinOnPenalties,
      @Param("pointsForLossOnPenalties") Integer pointsForLossOnPenalties);

  /**
   * Checks if a PointSystem with the same point values exists (as defined in
   * equals method)
   */
  @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PointSystem p WHERE p.pointsForWin = :pointsForWin "
      +
      "AND p.pointsForDraw = :pointsForDraw " +
      "AND p.pointsForLoss = :pointsForLoss " +
      "AND ((:pointsForWinOnPenalties IS NULL AND p.pointsForWinOnPenalties IS NULL) " +
      "OR p.pointsForWinOnPenalties = :pointsForWinOnPenalties) " +
      "AND ((:pointsForLossOnPenalties IS NULL AND p.pointsForLossOnPenalties IS NULL) " +
      "OR p.pointsForLossOnPenalties = :pointsForLossOnPenalties)")
  boolean existsByPointValues(
      @Param("pointsForWin") Integer pointsForWin,
      @Param("pointsForDraw") Integer pointsForDraw,
      @Param("pointsForLoss") Integer pointsForLoss,
      @Param("pointsForWinOnPenalties") Integer pointsForWinOnPenalties,
      @Param("pointsForLossOnPenalties") Integer pointsForLossOnPenalties);

  @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PointSystem p WHERE p.pointsForWin = :pointsForWin "
      +
      "AND p.pointsForDraw = :pointsForDraw " +
      "AND p.pointsForLoss = :pointsForLoss " +
      "AND ((:pointsForWinOnPenalties IS NULL AND p.pointsForWinOnPenalties IS NULL) " +
      "OR p.pointsForWinOnPenalties = :pointsForWinOnPenalties) " +
      "AND ((:pointsForLossOnPenalties IS NULL AND p.pointsForLossOnPenalties IS NULL) " +
      "OR p.pointsForLossOnPenalties = :pointsForLossOnPenalties) " +
      "AND p.id <> :id")
  boolean existsByPointValuesAndIdNot(
      @Param("pointsForWin") Integer pointsForWin,
      @Param("pointsForDraw") Integer pointsForDraw,
      @Param("pointsForLoss") Integer pointsForLoss,
      @Param("pointsForWinOnPenalties") Integer pointsForWinOnPenalties,
      @Param("pointsForLossOnPenalties") Integer pointsForLossOnPenalties,
      @Param("id") Long id);
}
