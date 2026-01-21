package com.eagle.futbolapi.features.structure.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.StructureType;
import com.eagle.futbolapi.features.structure.entity.Structure;

@Repository
public interface StructureRepository extends JpaRepository<Structure, Long> {

  Optional<Structure> findByName(String name);

  Optional<Structure> findByDisplayName(String displayName);

  Page<Structure> findByType(StructureType type, Pageable pageable);

  boolean existsByName(String name);

  boolean existsByDisplayName(String displayName);

  boolean existsByNameAndIdNot(String name, Long id);

  boolean existsByDisplayNameAndIdNot(String displayName, Long id);

}
