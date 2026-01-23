package com.eagle.futbolapi.features.match.service;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.match.dto.MatchDTO;
import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.match.mapper.MatchMapper;
import com.eagle.futbolapi.features.match.repository.MatchRepository;

@Service
@Transactional
public class MatchService extends BaseCrudService<Match, Long, MatchDTO> {

  protected MatchService(MatchRepository repository, MatchMapper mapper) {
    super(repository, mapper);
  }

  @Override
  protected boolean isDuplicate(@NotNull Match entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Match entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

}
