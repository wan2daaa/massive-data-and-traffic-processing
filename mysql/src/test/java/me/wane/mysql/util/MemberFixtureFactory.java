package me.wane.mysql.util;

import me.wane.mysql.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class MemberFixtureFactory {

  public static Member create() {
    EasyRandomParameters param = new EasyRandomParameters(); // 시드기반이라서 계속 같은 값을 줌

    return new EasyRandom(param).nextObject(Member.class);
  }

  public static Member create(Long seed) {
//    EasyRandomParameters param = new EasyRandomParameters(); // 시드기반이라서 계속 같은 값을 줌
    EasyRandomParameters param = new EasyRandomParameters().seed(seed);


    return new EasyRandom(param).nextObject(Member.class);
  }

}
