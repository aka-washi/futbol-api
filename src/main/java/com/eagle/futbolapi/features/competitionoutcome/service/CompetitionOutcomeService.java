package com.eagle.futbolapi.features.competitionoutcome.service;

import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.competitionoutcome.dto.CompetitionOutcomeDto;
import com.eagle.futbolapi.features.competitionoutcome.entity.CompetitionOutcome;
import com.eagle.futbolapi.features.competitionoutcome.mapper.CompetitionOutcomeMapper;
import com.eagle.futbolapi.features.competitionoutcome.repository.CompetitionOutcomeRepository;

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
