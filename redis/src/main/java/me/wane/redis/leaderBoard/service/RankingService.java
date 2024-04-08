package me.wane.redis.leaderBoard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RankingService {
  private final StringRedisTemplate redisTemplate;

  private static final String LEADER_BOARD = "leaderBoard";

  public boolean setUserScore(String userId, int score) {

    ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
    zSetOps.add(LEADER_BOARD, userId, score);

    return true;
  }

  public Long getUserRanking(String userId) {
    ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
    Long rank = zSetOps.reverseRank(LEADER_BOARD, userId);

    return rank;
  }

  public List<String> getTopRank(int limit) {
    ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
    Set<String> rangeSet = zSetOps.reverseRange(LEADER_BOARD, 0, limit - 1);

    return new ArrayList<>(rangeSet);
  }

}
