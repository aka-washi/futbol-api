package com.eagle.futbolapi.features.matchevent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.matchevent.entity.EventType;
import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;
import com.eagle.futbolapi.features.matchevent.entity.Period;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.team.entity.Team;

@Repository
public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {

    Page<MatchEvent> findByMatch(Match match, Pageable pageable);

    Page<MatchEvent> findByMatchId(Long matchId, Pageable pageable);

    Page<MatchEvent> findByTeam(Team team, Pageable pageable);

    Page<MatchEvent> findByTeamId(Long teamId, Pageable pageable);

    Page<MatchEvent> findByPlayer(Player player, Pageable pageable);

    Page<MatchEvent> findByPlayerId(Long playerId, Pageable pageable);

    Page<MatchEvent> findByType(EventType type, Pageable pageable);

    Page<MatchEvent> findByPeriod(Period period, Pageable pageable);

    @Query("SELECT me FROM MatchEvent me WHERE me.match.id = :matchId ORDER BY me.minute ASC")
    Page<MatchEvent> findByMatchIdOrderByMinute(@Param("matchId") Long matchId, Pageable pageable);

    Page<MatchEvent> findByMatchIdAndTeamId(Long matchId, Long teamId, Pageable pageable);

    Page<MatchEvent> findByMatchIdAndType(Long matchId, EventType type, Pageable pageable);

    Page<MatchEvent> findByPlayerIdAndType(Long playerId, EventType type, Pageable pageable);

    @Query("SELECT me FROM MatchEvent me WHERE me.match.id = :matchId AND me.minute BETWEEN :startMinute AND :endMinute ORDER BY me.minute ASC")
    Page<MatchEvent> findByMatchIdAndMinuteRange(@Param("matchId") Long matchId,
            @Param("startMinute") Integer startMinute, @Param("endMinute") Integer endMinute, Pageable pageable);

}
