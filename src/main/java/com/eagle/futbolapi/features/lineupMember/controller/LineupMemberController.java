package com.eagle.futbolapi.features.lineupMember.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.lineupMember.dto.LineupMemberDto;
import com.eagle.futbolapi.features.lineupMember.entity.LineupMember;
import com.eagle.futbolapi.features.lineupMember.mapper.LineupMemberMapper;
import com.eagle.futbolapi.features.lineupMember.service.LineupMemberService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for LineupMember entity operations.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/lineup-members")
public class LineupMemberController extends BaseCrudController<LineupMember, LineupMemberDto, LineupMemberService, LineupMemberMapper> {

  private static final String RESOURCE_NAME = "LineupMember";
  private static final String SUCCESS_MESSAGE = "LineupMember retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "LineupMember already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected LineupMemberController(LineupMemberService service, LineupMemberMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }
}
