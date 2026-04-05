package com.eagle.futbolapi.features.teambrand.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.teambrand.entity.TeamBrand;

@Repository
public interface TeamBrandRepository extends BaseRepository<TeamBrand, Long> {

  Optional<TeamBrand> findByDisplayName(String displayName);
}
