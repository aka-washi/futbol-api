package com.eagle.futbolapi.features.pointsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;

public interface PointSystemRepository extends JpaRepository<PointSystem, Long> {

    Optional<PointSystem> findByName(String name);

    Optional<PointSystem> findByDisplayName(String displayName);

    boolean existsByName(String name);

}
