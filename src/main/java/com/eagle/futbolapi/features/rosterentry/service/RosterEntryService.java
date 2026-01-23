package com.eagle.futbolapi.features.rosterentry.service;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.rosterentry.dto.RosterEntryDTO;
import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;

@Service
@Transactional
public class RosterEntryService extends BaseCrudService<RosterEntry, Long, RosterEntryDTO> {

  protected RosterEntryService(JpaRepository<RosterEntry, Long> repository,
      BaseMapper<RosterEntry, RosterEntryDTO> mapper) {
    super(repository, mapper);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected boolean isDuplicate(@NotNull RosterEntry entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull RosterEntry entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

}
