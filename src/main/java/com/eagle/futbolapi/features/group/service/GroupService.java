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
import com.eagle.futbolapi.features.stage.service.StageService;
import com.eagle.futbolapi.features.stage.entity.Stage;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for Group entity operations.
 */
@Slf4j
@Service
@Transactional
@Validated
public class GroupService extends BaseCrudService<Group, Long, GroupDto> {

  private final StageService stageService;

  public GroupService(
      GroupRepository repository,
      GroupMapper mapper,
      StageService stageService) {
    super(repository, mapper);
    this.stageService = stageService;
  }

  @Override
  protected void resolveRelationships(GroupDto dto, Group entity) {
    if (dto.getStageId() != null) {
      Stage stage = stageService.getById(dto.getStageId())
          .orElseThrow(() -> new com.eagle.futbolapi.features.base.exception.ResourceNotFoundException("Stage", "id", dto.getStageId()));
      entity.setStage(stage);
    } else if (dto.getStageDisplayName() != null && !dto.getStageDisplayName().trim().isEmpty()) {
      Stage stage = stageService.findByDisplayName(dto.getStageDisplayName())
          .orElseThrow(() -> new com.eagle.futbolapi.features.base.exception.ResourceNotFoundException("Stage", "displayName", dto.getStageDisplayName()));
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
