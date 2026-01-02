package com.eagle.futbolapi.features.shared.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.shared.BaseEntity;


public abstract class BaseCrudService<T extends BaseEntity, K> {
    protected final JpaRepository<T, K> repository;

    protected BaseCrudService(JpaRepository<T, K> repository) {
        this.repository = repository;
    }

    public Page<T> getAll(Pageable pageable) {
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        return repository.findAll(pageable);
    }

    public Optional<T> getById(K id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return repository.findById(id);
    }

    public T create(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        if(isDuplicate(entity)) {
            throw new IllegalArgumentException("Duplicate entity");
        }
        return repository.save(entity);
    }

    public T update(K id, T entity) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(entity, "Entity details cannot be null");

        if(isDuplicate(id, entity)) {
            throw new IllegalArgumentException("Duplicate entity");
        }
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Entity with given ID does not exist");
        }

        entity.setId((Long) id);

        return repository.save(entity);
    }

    public void delete(K id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        repository.deleteById(id);
    }

    public boolean existsById(K id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return repository.existsById(id);
    }

    protected abstract boolean isDuplicate(@NotNull T entity);
    protected abstract boolean isDuplicate(K id, @NotNull T entity);

}
