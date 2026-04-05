package com.eagle.futbolapi.features.qualificationoutcome.repository;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.qualificationoutcome.entity.QualificationOutcome;

@Repository
public interface QualificationOutcomeRepository extends BaseRepository<QualificationOutcome, Long> {

}
