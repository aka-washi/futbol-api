package com.eagle.futbolapi.features.lineup.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.lineup.entity.Lineup;
import com.eagle.futbolapi.features.lineup.repository.LineupRepository;
import com.eagle.futbolapi.features.match.service.MatchService;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.player.service.PlayerService;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class LineupService extends BaseCrudService<Lineup, Long> {
}
