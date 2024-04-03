package me.wane.mysql.domain.member;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import me.wane.mysql.domain.member.entity.Member;
import me.wane.mysql.util.MemberFixtureFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

  public static final Long DEFAULT_SEED = 1L;

  @DisplayName("회원은 닉네임을 변경할 수 있다.")
  @Test
  public void testChangeName() {
    Member member = MemberFixtureFactory.create(DEFAULT_SEED);
    String expected = "wane";

    member.changeNickname(expected);

    Assertions.assertThat(member.getNickname()).isEqualTo(expected);
  }

  @DisplayName("회원은 닉네임을 10자를 넘길 수 없다.")
  @Test
  public void testChangeNameOverLength() {
    Member member = MemberFixtureFactory.create(DEFAULT_SEED);
    String overMaxLength = "wanewane12345678";

    assertThatThrownBy(() -> member.changeNickname(overMaxLength))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("닉네임은 10자를 넘길 수 없습니다.");
  }

}
