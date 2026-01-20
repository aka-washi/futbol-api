package com.eagle.futbolapi.features.match.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.match.repository.MatchRepository;
import com.eagle.futbolapi.features.matchday.entity.Matchday;
import com.eagle.futbolapi.features.matchday.service.MatchdayService;
import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.person.service.PersonService;
import com.eagle.futbolapi.features.base.enums.MatchStatus;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.service.TeamService;
import com.eagle.futbolapi.features.venue.entity.Venue;
import com.eagle.futbolapi.features.venue.service.VenueService;

@Service
@Transactional
public class MatchService extends BaseCrudService<Match, Long> {

}
