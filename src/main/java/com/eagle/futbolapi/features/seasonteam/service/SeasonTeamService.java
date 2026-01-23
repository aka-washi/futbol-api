package com.eagle.futbolapi.features.seasonteam.service;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.seasonteam.dto.SeasonTeamDTO;
import com.eagle.futbolapi.features.seasonteam.entity.SeasonTeam;

@Service
@Transactional
public class SeasonTeamService extends BaseCrudService<SeasonTeam, Long, SeasonTeamDTO> {

  protected SeasonTeamService(JpaRepository<SeasonTeam, Long> repository,
      BaseMapper<SeasonTeam, SeasonTeamDTO> mapper) {
    super(repository, mapper);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected boolean isDuplicate(@NotNull SeasonTeam entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull SeasonTeam entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

}
