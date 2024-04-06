package me.wane.mysql.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.follow.entity.Follow;
import me.wane.mysql.domain.follow.service.FollowReadService;
import me.wane.mysql.domain.post.dto.PostCommand;
import me.wane.mysql.domain.post.service.PostWriteService;
import me.wane.mysql.domain.post.service.TimelineWriteService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreatePostUsecase {

  private final PostWriteService postWriteService;
  private final FollowReadService followReadService;
  private final TimelineWriteService timelineWriteService;

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
