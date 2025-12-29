package com.eagle.futbolapi.features.outcome.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.outcome.entity.Outcome;

public interface OutcomeRepository extends JpaRepository<Outcome, Long> {

}
