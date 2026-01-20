package com.eagle.futbolapi.features.stage.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.stage.entity.Stage;
import com.eagle.futbolapi.features.stage.repository.StageRepository;

@Service
@Transactional
public class StageService extends BaseCrudService<Stage, Long> {

}
