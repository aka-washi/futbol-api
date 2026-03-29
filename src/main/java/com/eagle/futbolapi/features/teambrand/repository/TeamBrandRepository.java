package com.eagle.futbolapi.features.teambrand.repository;

import java.util.Optional;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.teambrand.entity.TeamBrand;

public interface TeamBrandRepository extends BaseRepository<TeamBrand, Long> {

  Optional<TeamBrand> findByDisplayName(String displayName);
}
