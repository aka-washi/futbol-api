package com.eagle.futbolapi.features.standing.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.stage.entity.Stage;
import com.eagle.futbolapi.features.stage.service.StageService;
import com.eagle.futbolapi.features.standing.entity.Standing;
import com.eagle.futbolapi.features.standing.repository.StandingRepository;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class StandingService extends BaseCrudService<Standing, Long> {

}
