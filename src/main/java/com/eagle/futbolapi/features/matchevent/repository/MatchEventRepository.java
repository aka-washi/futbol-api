package com.eagle.futbolapi.features.matchevent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.MatchEventType;
import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;

@Repository
public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {

  boolean existsByMatchIdAndPlayerIdAndTypeAndMinute(Long matchId, Long playerId, MatchEventType type, Integer minute);

  boolean existsByMatchIdAndPlayerIdAndTypeAndMinuteAndIdNot(Long matchId, Long playerId, MatchEventType type,
      Integer minute, Long id);

}
