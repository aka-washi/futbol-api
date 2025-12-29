package com.eagle.futbolapi.features.match.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.match.entity.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

}
