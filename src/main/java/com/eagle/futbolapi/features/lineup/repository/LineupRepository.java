package com.eagle.futbolapi.features.lineup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.lineup.entity.Lineup;

@Repository
public interface LineupRepository extends JpaRepository<Lineup, Long> {

}
