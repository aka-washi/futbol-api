package com.eagle.futbolapi.features.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.team.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
