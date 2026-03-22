package com.eagle.futbolapi.features.base.mapper;

/**
 * Generic mapper interface for entity-to-DTO and DTO-to-entity conversions.
 *
 * @param <E> Entity type
 * @param <D> DTO type
 */
public interface BaseMapper<E, D> {

  /**
   * Converts an entity to a DTO.
   *
   * @param entity the entity to convert
   * @return the DTO
   */
  D toDTO(E entity);

  /**
   * Converts a DTO to an entity.
   *
   * @param dto the DTO to convert
   * @return the entity
   */
  E toEntity(D dto);

}
