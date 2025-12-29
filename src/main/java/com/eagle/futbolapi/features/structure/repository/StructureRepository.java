package com.eagle.futbolapi.features.structure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.structure.entity.Structure;

public interface StructureRepository extends JpaRepository<Structure, Long> {

}
