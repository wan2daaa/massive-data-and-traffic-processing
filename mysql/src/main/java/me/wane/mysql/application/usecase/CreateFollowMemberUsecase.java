package me.wane.mysql.application.usecase;

import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.follow.service.FollowWriteService;
import me.wane.mysql.domain.member.dto.MemberDto;
import me.wane.mysql.domain.member.service.MemberReadService;
import org.springframework.stereotype.Service;

/**
 * 각각 도메인들의 흐름을 제어하는 역할
 */
@RequiredArgsConstructor
@Service
public class CreateFollowMemberUsecase { // 클래스 명을 동사로 쓰는게 이 한기능을 한다는 것을 알 수 있어서

  private final MemberReadService memberReadService;
  private final FollowWriteService followWriteService;
  public void execute(Long fromMemberId, Long toMemberId){
    /**
     * 1. 입력받은 memberId로 회원 조회
     * 2. FollowWriteService.create()
     */
    MemberDto fromMember = memberReadService.getMember(fromMemberId);
    MemberDto toMember = memberReadService.getMember(toMemberId);

    followWriteService.create(fromMember, toMember);
  }

}
