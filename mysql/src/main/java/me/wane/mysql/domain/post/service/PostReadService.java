package me.wane.mysql.domain.post.service;

import java.util.List;
import java.util.OptionalLong;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.post.dto.DailyPostCount;
import me.wane.mysql.domain.post.dto.DailyPostCountRequest;
import me.wane.mysql.domain.post.dto.PostDto;
import me.wane.mysql.domain.post.entity.Post;
import me.wane.mysql.domain.post.repository.PostLikeRepository;
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
  private final PostLikeRepository postLikeRepository;

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

  public Post getPost(Long postId) {
    return postRepository.findById(postId, false).orElseThrow();
  }

  public Page<PostDto> getPosts(Long memberId, Pageable pageRequest ) {
    return postRepository.findAllByMemberId(memberId, pageRequest)
        .map(this::toDto);
  }

  private PostDto toDto(Post post) {
    return new PostDto(
        post.getId(),
        post.getContents(),
        post.getCreatedAt(),
        postLikeRepository.getCount(post.getId())
    );
  }

  public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
    List<Post> posts = findAllBy(memberId, cursorRequest);
    Long nextKey = getNextKey(posts);

    return new PageCursor<>(cursorRequest.next(nextKey), posts);

  }

  public PageCursor<Post> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {
    List<Post> posts = findAllBy(memberIds, cursorRequest);
    Long nextKey = getNextKey(posts);

    return new PageCursor<>(cursorRequest.next(nextKey), posts);
  }

  public List<Post> getPosts(List<Long> ids) {
    return postRepository.findAllByInId(ids);
  }

  private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
    if (cursorRequest.hasKey()) {
      return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(
          cursorRequest.key(), memberId, cursorRequest.size());
    }
    return postRepository.findAllByMemberIdAndOrderByIdDesc(
        memberId, cursorRequest.size());
  }

  private List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
    if (cursorRequest.hasKey()) {
      return postRepository.findAllByLessThanIdAndInMemberIdsAndOrderByIdDesc(
          cursorRequest.key(), memberIds, cursorRequest.size());
    }
    return postRepository.findAllByInMemberIdsAndOrderByIdDesc(
        memberIds, cursorRequest.size());
  }


  private static long getNextKey(List<Post> posts) {
    return posts.stream().mapToLong(Post::getId)
        .min()
        .orElse(CursorRequest.NONE_KEY);
  }

}
