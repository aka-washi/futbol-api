package com.eagle.futbolapi.features.stage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.stage.entity.Stage;

public interface StageRepository extends JpaRepository<Stage, Long> {

}
