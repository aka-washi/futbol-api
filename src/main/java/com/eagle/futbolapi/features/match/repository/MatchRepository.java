package com.eagle.futbolapi.features.match.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.MatchStatus;
import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.matchday.entity.Matchday;
import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.venue.entity.Venue;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

}
