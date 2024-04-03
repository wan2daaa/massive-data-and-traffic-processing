package me.wane.mysql.domain.follow.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

/**
 * 댓글이든 조회 할때 이름이 따라올텐데 이걸 팔로우 테이블에 넣어야할까? 아님 조인을 해야할까?
 *
 * 팔로우의 성격을 보면 항상 이름이랑 데이터가 따라 오는 것 같다.
 * 데이터의 최신성은 보장해야하나?
 *  - 닉네임 히스토리는 최신성을 보장하면 오히려 안됨
 *  - 팔로우는 3달전의 팔로우들을 보내주면 안되니까 정규화를 해야함
 *
 * 결국 데이터를 정규화 해보자
 *
 * 조인 쿼리를 가능하면 미루면 좋다
 *
 * follow 에서 member 를 조인하면 엄청난 강한 결합이 생긴다
 * 초기에서부터 강한 결합을 주게되면 유연성에서 좋지 않다.
 * 조인은 결합이 강하게 되서 아키텍처 관점에서 좋지 않다.
 *
 *
 *
 */
@Getter
public class Follow {

  private final Long id;

  private final Long fromMemberId;

  private final Long toMemberId;

  private final LocalDateTime createdAt;

  @Builder
  public Follow(Long id, Long fromMemberId, Long toMemberId, LocalDateTime createdAt) {
    this.id = id;
    this.fromMemberId = Objects.requireNonNull(fromMemberId);
    this.toMemberId = Objects.requireNonNull(toMemberId);
    this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
  }
}
