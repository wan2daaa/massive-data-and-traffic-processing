package me.wane.mysql.domain.post.service;

import java.util.List;
import java.util.OptionalLong;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.post.dto.DailyPostCount;
import me.wane.mysql.domain.post.dto.DailyPostCountRequest;
import me.wane.mysql.domain.post.entity.Post;
import me.wane.mysql.domain.post.repository.PostRepository;
import me.wane.mysql.util.CursorRequest;
import me.wane.mysql.util.PageCursor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public Page<Post> getPosts(Long memberId, Pageable pageRequest ) {
    return postRepository.findAllByMemberId(memberId, pageRequest);
  }

  public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
    List<Post> posts = findAllBy(memberId, cursorRequest);
    Long nextKey = posts.stream().mapToLong(Post::getId)
        .min()
        .orElse(CursorRequest.NONE_KEY);

    return new PageCursor<>(cursorRequest.next(nextKey), posts);

  }

  private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
    if (cursorRequest.hasKey()) {
      return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(
          cursorRequest.key(), memberId, cursorRequest.size());
    }
    return postRepository.findAllByMemberIdAndOrderByIdDesc(
        memberId, cursorRequest.size());
  }

}
