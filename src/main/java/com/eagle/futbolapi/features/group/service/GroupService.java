package com.eagle.futbolapi.features.group.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.group.dto.GroupDto;
import com.eagle.futbolapi.features.group.entity.Group;
import com.eagle.futbolapi.features.group.mapper.GroupMapper;
import com.eagle.futbolapi.features.group.repository.GroupRepository;
import com.eagle.futbolapi.features.stage.entity.Stage;
import com.eagle.futbolapi.features.stage.repository.StageRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for Group entity operations.
 */
@Slf4j
@Service
@Transactional
@Validated
public class GroupService extends BaseCrudService<Group, Long, GroupDto> {

  private final StageRepository stageRepository;

  public GroupService(
      GroupRepository repository,
      GroupMapper mapper,
      StageRepository stageRepository) {
    super(repository, mapper);
    this.stageRepository = stageRepository;
  }

  @Override
  protected void resolveRelationships(@NotNull GroupDto dto, @NotNull Group entity) {
    if (dto.getStageId() != null) {
      Stage stage = stageRepository.findById(dto.getStageId())
          .orElseThrow(() -> new IllegalArgumentException(
              "Stage not found with id: " + dto.getStageId()));
      entity.setStage(stage);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Group group) {
    Objects.requireNonNull(group, "Group cannot be null");
    return isDuplicate(group, UniquenessStrategy.ANY);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Group group) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(group, "Group cannot be null");
    return isDuplicate(id, group, UniquenessStrategy.ANY);
  }
}
