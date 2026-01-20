package com.eagle.futbolapi.features.competition.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.competition.repository.CompetitionRepository;
import com.eagle.futbolapi.features.base.enums.CompetitionType;
import com.eagle.futbolapi.features.base.exception.DuplicateResourceException;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;

@Service
@Transactional
public class CompetitionService extends BaseCrudService<Competition, Long> {

}
