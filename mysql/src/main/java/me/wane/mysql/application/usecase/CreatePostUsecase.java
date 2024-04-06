package me.wane.mysql.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.follow.entity.Follow;
import me.wane.mysql.domain.follow.service.FollowReadService;
import me.wane.mysql.domain.post.dto.PostCommand;
import me.wane.mysql.domain.post.service.PostWriteService;
import me.wane.mysql.domain.post.service.TimelineWriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreatePostUsecase {

  private final PostWriteService postWriteService;
  private final FollowReadService followReadService;
  private final TimelineWriteService timelineWriteService;

  @Transactional // 트랜잭션도 비용이 드는 작업이다! 이 undoLog 를 유지해야 하는 시간이 더 길어져서 성능에 안좋은 영향을 가질 수 있다.
  //TODO : propagation level 공부
  public Long execute(PostCommand postCommand) {
    Long postId = postWriteService.create(postCommand);
    List<Long> followerMemberIds = followReadService.getFollowers(postCommand.memberId())
        .stream()
        .map(Follow::getFromMemberId)
        .toList();
    timelineWriteService.deliveryToTimeline(postId, followerMemberIds);

    return postId;
  }

}
