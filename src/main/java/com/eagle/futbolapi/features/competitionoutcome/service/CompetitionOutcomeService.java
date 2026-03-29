package com.eagle.futbolapi.features.competitionoutcome.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.competition.repository.CompetitionRepository;
import com.eagle.futbolapi.features.competitionoutcome.dto.CompetitionOutcomeDto;
import com.eagle.futbolapi.features.competitionoutcome.entity.CompetitionOutcome;
import com.eagle.futbolapi.features.competitionoutcome.mapper.CompetitionOutcomeMapper;
import com.eagle.futbolapi.features.competitionoutcome.repository.CompetitionOutcomeRepository;

import jakarta.validation.constraints.NotNull;

public class CompetitionOutcomeService extends BaseCrudService<CompetitionOutcome, Long, CompetitionOutcomeDto> {

  private final CompetitionOutcomeRepository repository;

  protected CompetitionOutcomeService(CompetitionOutcomeRepository repository,
     CompetitionOutcomeMapper mapper) {
    super(repository, mapper);
    this.repository = repository;

    //TODO Auto-generated constructor stub
  }

  @Override
  protected boolean isDuplicate(@NotNull CompetitionOutcome entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull CompetitionOutcome entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

}
