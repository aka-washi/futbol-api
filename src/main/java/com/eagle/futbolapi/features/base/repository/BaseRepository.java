package com.eagle.futbolapi.features.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.eagle.futbolapi.features.base.entity.BaseEntity;

/**
 * Base repository interface that combines JpaRepository with JpaSpecificationExecutor
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, K> extends JpaRepository<T, K>, JpaSpecificationExecutor<T> {
}
