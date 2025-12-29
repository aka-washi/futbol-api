package com.eagle.futbolapi.features.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.team.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
