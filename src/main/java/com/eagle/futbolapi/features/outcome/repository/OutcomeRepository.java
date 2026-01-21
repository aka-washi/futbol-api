package com.eagle.futbolapi.features.outcome.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.outcome.entity.Outcome;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Long> {

}
