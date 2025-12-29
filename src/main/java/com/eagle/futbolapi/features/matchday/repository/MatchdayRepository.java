package com.eagle.futbolapi.features.matchday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.matchday.entity.Matchday;

public interface MatchdayRepository extends JpaRepository<Matchday, Long> {

}
