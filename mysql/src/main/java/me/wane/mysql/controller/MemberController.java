package me.wane.mysql.controller;

import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.member.dto.RegisterMemberCommand;
import me.wane.mysql.domain.member.service.MemberWriteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberWriteService memberWriteService;

  @PostMapping("/members")
  public void register(@RequestBody RegisterMemberCommand command) {
    memberWriteService.create(command);
  }

}
