package com.eagle.futbolapi.features.matchevent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.EventType;
import com.eagle.futbolapi.features.base.enums.Period;
import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.team.entity.Team;

@Repository
public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {

}
