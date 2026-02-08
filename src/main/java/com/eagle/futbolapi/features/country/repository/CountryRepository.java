package com.eagle.futbolapi.features.country.repository;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.country.entity.Country;

@Repository
public interface CountryRepository extends BaseRepository<Country, Long> {

}
