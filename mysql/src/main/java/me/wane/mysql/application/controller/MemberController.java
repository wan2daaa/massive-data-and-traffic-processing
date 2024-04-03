package me.wane.mysql.application.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.member.dto.MemberDto;
import me.wane.mysql.domain.member.dto.MemberNicknameHistoryDto;
import me.wane.mysql.domain.member.dto.RegisterMemberCommand;
import me.wane.mysql.domain.member.entity.Member;
import me.wane.mysql.domain.member.service.MemberReadService;
import me.wane.mysql.domain.member.service.MemberWriteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * JPA인 경우 엔티티를 그대로 리턴하는 경우 OSIV 같은 이슈 발생 가능 레이어드 아키텍처에서 엔티티는 제일 깊은 레이어에 위치하는데 이걸 내보내면 안됨 불필요한 정보도 다
 * 내려줘서 dto로 내려주는게 좋음
 */
@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberWriteService memberWriteService;
  private final MemberReadService memberReadService;

  @PostMapping("/members")
  public MemberDto register(@RequestBody RegisterMemberCommand command) {
    Member member = memberWriteService.register(command);
    return memberReadService.toDto(member);
  }

  @GetMapping("/members/{id}")
  public MemberDto getMember(@PathVariable Long id) {
    return memberReadService.getMember(id);
  }

  @PostMapping("/{id}/name")
  public MemberDto changeNickname(@PathVariable Long id, @RequestBody String nickname) {
    memberWriteService.changeNickname(id, nickname);
    return memberReadService.getMember(id);
  }

  @GetMapping("/{memberId}/nickname/histories")
  public List<MemberNicknameHistoryDto> getNicknameHistories(@PathVariable Long memberId) {
    return memberReadService.getNicknameHistoriesByMemberId(memberId);
  }

}
