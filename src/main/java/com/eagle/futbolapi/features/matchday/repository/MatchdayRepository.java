package com.eagle.futbolapi.features.matchday.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.matchday.entity.Matchday;

@Repository
public interface MatchdayRepository extends JpaRepository<Matchday, Long> {

  Optional<Matchday> findByName(String name);

  Optional<Matchday> findByDisplayName(String displayName);

  Optional<Matchday> findByUniqueValues(String name, Long stageId);

  boolean existsByUniqueValues(String name, Long stageId);

  boolean existsByUniqueValuesAndIdNot(String name, Long stageId, Long id);
}
