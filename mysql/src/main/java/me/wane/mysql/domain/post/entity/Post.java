package me.wane.mysql.domain.post.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

@ToString
@Getter
public class Post {

  private final Long id;

  private final Long memberId;

  private final String contents;

  private final LocalDate createdDate;

  private Long likeCount;

  private Long version;

  private final LocalDateTime createdAt;

  @Builder
  public Post(Long id, Long memberId, String contents, LocalDate createdDate, Long likeCount,
      Long version,
      LocalDateTime createdAt) {
    this.id = id;
    this.memberId = Objects.requireNonNull(memberId);
    this.contents = Objects.requireNonNull(contents);
    this.createdDate = createdDate == null ? LocalDate.now() : createdDate;
    this.likeCount = likeCount == null ? 0 : likeCount; // default 컬럼을 추가 하게 되면 만약 새로운컬럼 추가시 모든 데이터에 락이 걸릴 수 있기 떄문에, bulkInsert로 나눠서 넣어주거나, 이렇게 자바 코드에서 넣어주는 식으로 처리
    this.version = version == null ? 0 : version;
    this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
  }

  public void incrementLikeCount() {
    likeCount += 1;
  }
}
