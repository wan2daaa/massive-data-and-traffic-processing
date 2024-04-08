package me.wane.redis.leaderBoard.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.redis.leaderBoard.service.RankingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/leaderboard")
public class LeaderBoardController {

  private final RankingService rankingService;

  @GetMapping("/setScore")
  public Boolean setScore(
      @RequestParam String userId,
      @RequestParam int score
  ) {
    return rankingService.setUserScore(userId, score);
  }

  @GetMapping("/getRank")
  public Long getUserRank(@RequestParam String userId) {
    return rankingService.getUserRanking(userId);
  }

  @GetMapping("/getTopRanks")
  public List<String> getTopRank(@RequestParam(defaultValue = "10") int limit) {
    return rankingService.getTopRank(limit);
  }


}
