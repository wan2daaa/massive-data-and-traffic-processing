package me.wane.mysql.application.usecase;

import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.member.dto.MemberDto;
import me.wane.mysql.domain.member.service.MemberReadService;
import me.wane.mysql.domain.post.entity.Post;
import me.wane.mysql.domain.post.service.PostLikeWriteService;
import me.wane.mysql.domain.post.service.PostReadService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreatePostLikeUsecase {

  private final PostReadService postReadService;
  private final MemberReadService memberReadService;
  private final PostLikeWriteService postLikeWriteService;

  public void execute(Long postId, Long memberId) {
    Post post = postReadService.getPost(postId);
    MemberDto member = memberReadService.getMember(memberId);
    postLikeWriteService.create(post, member);
  }

}
