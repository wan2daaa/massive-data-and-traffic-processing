package me.wane.mysql.domain.post.service;

import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.member.dto.MemberDto;
import me.wane.mysql.domain.post.dto.PostCommand;
import me.wane.mysql.domain.post.entity.Post;
import me.wane.mysql.domain.post.entity.PostLike;
import me.wane.mysql.domain.post.repository.PostLikeRepository;
import me.wane.mysql.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostLikeWriteService {

  private final PostLikeRepository postLikeRepository;

  public Long create(Post post , MemberDto memberDto) {
    PostLike postLike = PostLike.builder()
        .postId(post.getId())
        .memberId(memberDto.id())
        .build();

    return postLikeRepository.save(postLike).getPostId();
  }

}
