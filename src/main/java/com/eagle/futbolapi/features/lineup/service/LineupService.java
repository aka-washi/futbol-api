package com.eagle.futbolapi.features.lineup.service;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.lineup.dto.LineupDTO;
import com.eagle.futbolapi.features.lineup.entity.Lineup;

@Service
@Transactional
public class LineupService extends BaseCrudService<Lineup, Long, LineupDTO> {

  protected LineupService(JpaRepository<Lineup, Long> repository, BaseMapper<Lineup, LineupDTO> mapper) {
    super(repository, mapper);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected boolean isDuplicate(@NotNull Lineup entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Lineup entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }
}
