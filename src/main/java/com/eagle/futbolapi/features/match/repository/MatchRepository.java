package com.eagle.futbolapi.features.match.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.match.entity.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

}
