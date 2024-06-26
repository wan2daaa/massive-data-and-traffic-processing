package me.wane.mysql.application.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.application.usecase.CreatePostLikeUsecase;
import me.wane.mysql.application.usecase.CreatePostUsecase;
import me.wane.mysql.application.usecase.GetTimelinePostUsecase;
import me.wane.mysql.domain.post.dto.DailyPostCount;
import me.wane.mysql.domain.post.dto.DailyPostCountRequest;
import me.wane.mysql.domain.post.dto.PostCommand;
import me.wane.mysql.domain.post.dto.PostDto;
import me.wane.mysql.domain.post.entity.Post;
import me.wane.mysql.domain.post.service.PostReadService;
import me.wane.mysql.domain.post.service.PostWriteService;
import me.wane.mysql.util.CursorRequest;
import me.wane.mysql.util.PageCursor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

  private final PostWriteService postWriteService;
  private final PostReadService postReadService;
  private final GetTimelinePostUsecase getTimelinePostUsecase;
  private final CreatePostUsecase createPostUsecase;
  private final CreatePostLikeUsecase createPostLikeUsecase;

  @PostMapping
  public Long create(PostCommand command) {

//    return postWriteService.create(command);
    return createPostUsecase.execute(command);
  }

  @GetMapping("/daily-post-counts")
  public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest request) {
    return postReadService.getDailyPostCount(request);
  }

  @GetMapping("/members/{memberId}")
  public Page<PostDto> getPosts(@PathVariable("memberId") Long memberId, Pageable pageable) {
    return postReadService.getPosts(memberId, pageable);
  }

  @GetMapping("/members/{memberId}/by-cursor")
  public PageCursor<Post> getPostsByCursor(@PathVariable("memberId") Long memberId,
      CursorRequest cursorRequest) {
    return postReadService.getPosts(memberId, cursorRequest);
  }

  @GetMapping("/members/{memberId}/timeline")
  public PageCursor<Post> getTimeline(@PathVariable("memberId") Long memberId,
      CursorRequest cursorRequest) {
//    return getTimelinePostUsecase.execute(memberId, cursorRequest);
    return getTimelinePostUsecase.executeByTimeline(memberId, cursorRequest);
  }

  @PostMapping("/{postId}/like/v1")
  public void likePost(@PathVariable("postId") Long postId) {
    postWriteService.likePost(postId);
  }

  @PostMapping("/{postId}/like/optimistic")
  public void likePostByOptimisticLock(@PathVariable("postId") Long postId) {
    postWriteService.likePostByOptimisticLock(postId);
  }

  @PostMapping("/{postId}/like/v2")
  public void likePostV2(@PathVariable("postId") Long postId, @RequestParam("memberId") Long memberId) {
    createPostLikeUsecase.execute(postId, memberId);
  }

}
