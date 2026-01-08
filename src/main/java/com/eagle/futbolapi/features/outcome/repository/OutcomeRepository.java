package com.eagle.futbolapi.features.outcome.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.outcome.entity.Outcome;
import com.eagle.futbolapi.features.outcome.entity.OutcomeType;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.team.entity.Team;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Long> {

    List<Outcome> findByCompetition(Competition competition);

    List<Outcome> findByCompetitionId(Long competitionId);

    List<Outcome> findByTeam(Team team);

    List<Outcome> findByTeamId(Long teamId);

    List<Outcome> findByPlayer(Player player);

    List<Outcome> findByPlayerId(Long playerId);

    List<Outcome> findByStaff(Staff staff);

    List<Outcome> findByStaffId(Long staffId);

    List<Outcome> findByOutcomeType(OutcomeType outcomeType);

    List<Outcome> findByCompetitionIdAndOutcomeType(Long competitionId, OutcomeType outcomeType);

    List<Outcome> findByCompetitionIdAndTeamId(Long competitionId, Long teamId);

    List<Outcome> findByCompetitionIdAndPlayerId(Long competitionId, Long playerId);

    List<Outcome> findByCompetitionIdAndStaffId(Long competitionId, Long staffId);

    List<Outcome> findByTeamIdAndOutcomeType(Long teamId, OutcomeType outcomeType);

    List<Outcome> findByPlayerIdAndOutcomeType(Long playerId, OutcomeType outcomeType);

}
