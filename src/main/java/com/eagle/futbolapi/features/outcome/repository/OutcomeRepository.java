package com.eagle.futbolapi.features.outcome.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.OutcomeType;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.outcome.entity.Outcome;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.team.entity.Team;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Long> {

}
