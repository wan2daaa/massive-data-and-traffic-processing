package me.wane.mysql.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.follow.entity.Follow;
import me.wane.mysql.domain.follow.service.FollowReadService;
import me.wane.mysql.domain.post.entity.Post;
import me.wane.mysql.domain.post.entity.TimeLine;
import me.wane.mysql.domain.post.service.PostReadService;
import me.wane.mysql.domain.post.service.TimelineReadService;
import me.wane.mysql.util.CursorRequest;
import me.wane.mysql.util.PageCursor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetTimelinePostUsecase {

  private final FollowReadService followReadService;
  private final PostReadService postReadService;
  private final TimelineReadService timelineReadService;

  public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
    /*
        1. memberId -> follow 조회
        2. 1번 결과로 게시물 조회
     */
    List<Follow> followings = followReadService.getFollowings(memberId);
    List<Long> followingIds = followings.stream().map(Follow::getToMemberId).toList();
    return postReadService.getPosts(followingIds, cursorRequest);
  }

  public PageCursor<Post> executeByTimeline(Long memberId, CursorRequest cursorRequest) {
    /*
        1. Timeline 조회
        2. 1번에 해당하는 게시물을 조회한다.
     */
    PageCursor<TimeLine> pagedTimeLines = timelineReadService.getTimeLines(memberId, cursorRequest);
    List<Long> postIds = pagedTimeLines.body().stream().map(TimeLine::getPostId).toList();
    List<Post> posts = postReadService.getPosts(postIds);

    return new PageCursor<>(pagedTimeLines.nextCursorRequest(), posts);


  }


}
