package me.wane.mysql.domain.post.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.post.dto.DailyPostCount;
import me.wane.mysql.domain.post.dto.DailyPostCountRequest;
import me.wane.mysql.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostReadService {

  private final PostRepository postRepository;

  public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
    /**
     * 반환값 -> 리스트 [작성 일자, 작성회원, 작성 게시물 갯수]
     *
     * 두가지 방법
     * 1. group by로 조회해서 반환
     * select createdDate, memberId, count(id)
     * from Post
     * where memberId = :memberId and createdDate between :firstDate and :lastDate
     * group by createdDate memberId
     */
    return postRepository.groupByCreatedDate(request);
  }

}
