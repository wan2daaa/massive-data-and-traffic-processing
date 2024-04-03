package me.wane.mysql.domain.member.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

/**
 * 히스토리성 데이터는 정규화 대상이 아니다
 * 히스토리성 데이터는 변경이력을 추적하기 위한 용도로 사용된다.
 *
 * 정규화를 할려면 항상 데이터의 최신성을 보장하는 데이터 인지 고려해야함
 * e.x. 이커머스를 하고 있으면, 주문 내역에 제조사 이름을 남기는데,
 *     -> 제조사 이름이 변경되면 바뀐이름으로 들어가야할까? 남아야 할까?
 *     -> 이 데이터가 최신성을 보장해야하는지
 *     -> 이게 애매하면 PO, PM 분께 계속 물어봐야함
 *
 */

@Getter
public class MemberNicknameHistory {

  private final Long id;

  private final Long memberId;

  private final String nickname;

  private final LocalDateTime createdAt;

  @Builder
  public MemberNicknameHistory(Long id, Long memberId, String nickname, LocalDateTime createdAt) {
    this.id = id;
    this.memberId = Objects.requireNonNull(memberId);
    this.nickname = Objects.requireNonNull(nickname);
    this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
  }
}
