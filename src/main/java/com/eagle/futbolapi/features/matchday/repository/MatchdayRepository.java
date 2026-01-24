package com.eagle.futbolapi.features.matchday.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.matchday.entity.Matchday;

@Repository
public interface MatchdayRepository extends JpaRepository<Matchday, Long> {

  Optional<Matchday> findByName(String name);

  Optional<Matchday> findByDisplayName(String displayName);

  @Query("SELECT m FROM Matchday m WHERE m.name = :name AND m.stage.id = :stageId")
  Optional<Matchday> findByUniqueValues(@Param("name") String name, @Param("stageId") Long stageId);

  @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Matchday m WHERE m.name = :name AND m.stage.id = :stageId")
  boolean existsByUniqueValues(@Param("name") String name, @Param("stageId") Long stageId);

  @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Matchday m WHERE m.name = :name AND m.stage.id = :stageId AND m.id <> :id")
  boolean existsByUniqueValuesAndIdNot(@Param("name") String name, @Param("stageId") Long stageId,
      @Param("id") Long id);
}
