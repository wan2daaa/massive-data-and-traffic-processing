package me.wane.mysql.application.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.application.usecase.CreateFollowMemberUsecase;
import me.wane.mysql.application.usecase.GetFollowingMembersUsecase;
import me.wane.mysql.domain.member.dto.MemberDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {

  private final CreateFollowMemberUsecase createFollowMemberUsecase;
  private final GetFollowingMembersUsecase getFollowingMembersUsecase;

  @PostMapping("/{fromId}/{toId}")
  public void create(@PathVariable Long fromId, @PathVariable Long toId) {
    createFollowMemberUsecase.execute(fromId, toId);
  }

  @GetMapping("/members/{fromId}")
  public List<MemberDto> create(@PathVariable Long fromId) {
    return getFollowingMembersUsecase.execute(fromId);
  }

}
