package com.eagle.futbolapi.features.seasonteam.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.season.service.SeasonService;
import com.eagle.futbolapi.features.seasonteam.entity.SeasonTeam;
import com.eagle.futbolapi.features.seasonteam.repository.SeasonTeamRepository;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class SeasonTeamService extends BaseCrudService<SeasonTeam, Long> {

}
