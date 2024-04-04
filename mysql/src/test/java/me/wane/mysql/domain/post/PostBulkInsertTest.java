package me.wane.mysql.domain.post;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;
import me.wane.mysql.domain.post.entity.Post;
import me.wane.mysql.domain.post.repository.PostRepository;
import me.wane.mysql.util.PostFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

@SpringBootTest
public class PostBulkInsertTest {

  @Autowired
  private PostRepository postRepository;

  @Test
  void bulkInsert() {

    EasyRandom easyRandom = PostFixtureFactory.get(
        1L,
        LocalDate.of(1970, 1, 1),
        LocalDate.of(2024, 2, 1));

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    List<Post> posts = IntStream.range(0, 300_0000)
        .parallel() // 병렬로 실행시켜서 객체 생서 속도 증가
        .mapToObj(i -> easyRandom.nextObject(Post.class)).toList();

    stopWatch.stop();
    System.out.println("stopWatch.getTotalTimeSeconds() = " + stopWatch.getTotalTimeSeconds());

    StopWatch queryStopWatch = new StopWatch();

    queryStopWatch.start();
    postRepository.bulkInsert(posts);
    queryStopWatch.stop();
    System.out.println(
        "queryStopWatch.getTotalTimeSeconds() = " + queryStopWatch.getTotalTimeSeconds());
  }

  //

}
