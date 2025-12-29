package com.eagle.futbolapi.features.standing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.standing.entity.Standing;

public interface StandingRepository extends JpaRepository<Standing, Long> {

}
