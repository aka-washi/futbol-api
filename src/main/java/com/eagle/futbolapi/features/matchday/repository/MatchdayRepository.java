package com.eagle.futbolapi.features.matchday.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.matchday.entity.Matchday;

@Repository
public interface MatchdayRepository extends BaseRepository<Matchday, Long> {

  Optional<Matchday> findByName(String name);

  Optional<Matchday> findByDisplayName(String displayName);

  // Unique field methods: stage + number
  Optional<Matchday> findByStageIdAndNumber(Long stageId, Integer number);

  boolean existsByStageIdAndNumber(Long stageId, Integer number);

  boolean existsByStageIdAndNumberAndIdNot(Long stageId, Integer number, Long id);
}
