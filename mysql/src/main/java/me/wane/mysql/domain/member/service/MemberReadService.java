package me.wane.mysql.domain.member.service;

import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.member.dto.MemberDto;
import me.wane.mysql.domain.member.entity.Member;
import me.wane.mysql.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberReadService {

  private final MemberRepository memberRepository;

  public MemberDto getMember(Long id) {
    Member member = memberRepository.findById(id)
        .orElseThrow();
    return toDto(member);

  }

  public MemberDto toDto(Member member) {
    return new MemberDto(member.getId(), member.getEmail(), member.getNickname(),
        member.getBirthday());
  }

}
