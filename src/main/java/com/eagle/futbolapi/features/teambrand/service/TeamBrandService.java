package com.eagle.futbolapi.features.teambrand.service;

import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.teambrand.dto.TeamBrandDto;
import com.eagle.futbolapi.features.teambrand.entity.TeamBrand;
import com.eagle.futbolapi.features.teambrand.repository.TeamBrandRepository;

public class TeamBrandService extends BaseCrudService<TeamBrand, Long, TeamBrandDto> {

  private final TeamBrandRepository repository;

  protected TeamBrandService(TeamBrandRepository repository, BaseMapper<TeamBrand, TeamBrandDto> mapper) {
    super(repository, mapper);
    this.repository = repository;
  }

  public TeamBrand findByDisplayName(String displayName) {
    return repository.findByDisplayName(displayName)
        .orElseThrow(() -> new RuntimeException("TeamBrand with display name '" + displayName + "' not found"));
  }

  @Override
  protected boolean isDuplicate(@NotNull TeamBrand entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull TeamBrand entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

}
