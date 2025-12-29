package com.eagle.futbolapi.features.seasonteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.seasonteam.entity.SeasonTeam;

public interface SeasonTeamRepository extends JpaRepository<SeasonTeam, Long> {

}
