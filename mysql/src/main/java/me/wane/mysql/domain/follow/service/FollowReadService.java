package me.wane.mysql.domain.follow.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.follow.entity.Follow;
import me.wane.mysql.domain.follow.repository.FollowRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FollowReadService {

  private final FollowRepository followRepository;

  public List<Follow> getFollowings(Long memberId) { //아직 외부 서비스에 흘러들어가는게 없어서 dto 없어도 될듯
    return followRepository.findAllByFromMemberId(memberId);
  }

}
