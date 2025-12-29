package com.eagle.futbolapi.features.pointsystem.service;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;
import com.eagle.futbolapi.features.pointsystem.repository.PointSystemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PointSystemService {

    private final PointSystemRepository pointSystemRepository;

    public Page<PointSystem> getAllPointSystems(Pageable pageable) {
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        return pointSystemRepository.findAll(pageable);
    }

    public Optional<PointSystem> getPointSystemById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return pointSystemRepository.findById(id);
    }

    public Optional<PointSystem> getPointSystemByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Point system name cannot be null or empty");
        }
        return pointSystemRepository.findByName(name);
    }

    public Optional<PointSystem> getPointSystemByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Point system display name cannot be null or empty");
        }
        return pointSystemRepository.findByDisplayName(displayName);
    }

}
