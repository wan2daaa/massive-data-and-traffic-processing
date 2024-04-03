package me.wane.mysql.domain.follow.service;


import java.util.Objects;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.follow.entity.Follow;
import me.wane.mysql.domain.follow.repository.FollowRepository;
import me.wane.mysql.domain.member.dto.MemberDto;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 서로 다른 도메인의 데이터를 주고 받아야 할 때
 * 서로 다른 도메인의 흐름을 제어 할 때
 * - 헥사고날 아키텍처, DDD, ...
 * - 심플하게 application 레이어를 두고 usecase 레이어로 작업
 */
@RequiredArgsConstructor
@Service
public class FollowWriteService {

  private final FollowRepository followRepository;

//  public void create(Long fromMemberId, Long toMemberId) {
  public void create(MemberDto fromMember, MemberDto toMember) {
    /**
     * from, to 회원 정보를 받아서 (식별자로? 아님 어떤걸로?) <- 그럼 식별자에 해당하는 id 가 실제로 member가 존재하는지 검증하려면 여기서 검증을 해야해서 조금 별로다 msa 처럼 하나의 서버처럼 최대한 분리하자
     * 저장할텐데...
     * from <-> to validate (같은지)
     */
    Assert.isTrue(!fromMember.id().equals(toMember.id()), "From, To 회원이 동일 합니다.");

    Follow follow = Follow.builder()
        .fromMemberId(fromMember.id())
        .toMemberId(toMember.id())
        .build();

    followRepository.save(follow);

  }

}
