package me.wane.mysql.domain.member.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class Member {

  private final Long id;

  private final String nickname;

  private final String email;

  private final LocalDate birthday;

  private final LocalDateTime createdAt;

  private final static Long NAME_MAX_LENGTH = 10L;

  @Builder
  public Member(Long id, String nickname, String email, LocalDate birthday,
      LocalDateTime createdAt) {
    this.id = id;
    this.email = Objects.requireNonNull(email);
    this.birthday = Objects.requireNonNull(birthday);

    validateNickname(nickname);
    this.nickname = Objects.requireNonNull(nickname);
    this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
  }

  public void validateNickname(String nickname) {
    Assert.isTrue(nickname.length() <= NAME_MAX_LENGTH, "닉네임은 10자를 넘길 수 없습니다."); // TODO. Custom Exception 넣기
  }
}
