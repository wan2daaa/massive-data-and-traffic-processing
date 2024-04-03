package me.wane.mysql.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.follow.entity.Follow;
import me.wane.mysql.domain.follow.service.FollowReadService;
import me.wane.mysql.domain.member.dto.MemberDto;
import me.wane.mysql.domain.member.service.MemberReadService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetFollowingMembersUsecase {

  private final MemberReadService memberReadService;
  private final FollowReadService followReadService;

  public List<MemberDto> execute(Long memberId) {
    /**
     * fromMemberId = memberId -> Follow list
     * 2. 1번을 순회하면서 회원 정보를 찾으면 된다!
     */
    List<Follow> followings = followReadService.getFollowings(memberId);
    List<Long> followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();

    return memberReadService.getMembers(followingMemberIds);
  }

}
