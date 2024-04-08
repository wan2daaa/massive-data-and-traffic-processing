package me.wane.redis.leaderBoard;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.wane.redis.leaderBoard.service.RankingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

@SpringBootTest
class LeaderBoardSimpleTest {

  @Autowired
  private RankingService rankingService;

  //just do only once
  @Test
  void insertScore() throws Exception {
    for (int i = 0; i < 100_0000; i++) {
      int score = (int) (Math.random() * 1000000); // 0 ~ 999999
      String userId = "user_" + i;
      rankingService.setUserScore(userId, score);
    }
  }

  @Test
  void getRanks() {

    rankingService.getTopRank(1); // 네트워크 속도 최적화 위해


    //1
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    Long userRank = rankingService.getUserRanking("user_101");
    stopWatch.stop();

    long totalTimeNanos = stopWatch.getTotalTimeNanos();
    System.out.printf("Rank(%d) - Took %d ns%n", userRank, totalTimeNanos);


//    //2
    stopWatch.start("2");
    List<String> topRankers = rankingService.getTopRank(10);
    stopWatch.stop();

    System.out.printf("Range - Took %d ns%n", stopWatch.getTotalTimeNanos());

  }

  @Test
  void redisSortedSetPerformance() throws Exception {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    rankingService.getTopRank(100_0000);
    stopWatch.stop();
  }
  
  
  @Test
  void inMemorySortPerformance() {
    ArrayList<Integer> list = new ArrayList<>();
    for (int i = 0; i < 100_0000; i++) {
      int score = (int) (Math.random() * 1000000); // 0 ~ 999999
      list.add(score);
    }

    StopWatch stopWatch = new StopWatch();

    stopWatch.start();
    Collections.sort(list); //nlogn
    stopWatch.stop();

    long arrayListSortTime = stopWatch.getTotalTimeMillis();
    System.out.println("arrayListSortTime = " + arrayListSortTime+"ms");
  }


  
  



}