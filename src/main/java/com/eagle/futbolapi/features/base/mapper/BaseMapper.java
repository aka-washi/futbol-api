package com.eagle.futbolapi.features.base.mapper;

/**
 * Generic mapper interface for entity-to-DTO and DTO-to-entity conversions.
 */
public interface BaseMapper<E, D> {

  /**
 * Converts an entity to a DTO.
 */
  D toDto(E entity);

  /**
 * Converts a DTO to an entity.
 */
  E toEntity(D dto);

}
