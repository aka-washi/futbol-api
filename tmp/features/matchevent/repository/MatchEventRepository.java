package com.eagle.futbolapi.features.matchevent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.EventType;
import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;

@Repository
public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {

  boolean existsByMatchIdAndPlayerIdAndTypeAndMinute(Long matchId, Long playerId, EventType type, Integer minute);

  boolean existsByMatchIdAndPlayerIdAndTypeAndMinuteAndIdNot(Long matchId, Long playerId, EventType type,
      Integer minute, Long id);

}
