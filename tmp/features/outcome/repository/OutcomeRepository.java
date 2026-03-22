package com.eagle.futbolapi.features.outcome.repository;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.outcome.entity.Outcome;

@Repository
public interface OutcomeRepository extends BaseRepository<Outcome, Long> {

}
