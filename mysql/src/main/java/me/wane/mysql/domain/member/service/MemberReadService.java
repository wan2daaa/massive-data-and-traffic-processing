package me.wane.mysql.domain.member.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.member.dto.MemberDto;
import me.wane.mysql.domain.member.dto.MemberNicknameHistoryDto;
import me.wane.mysql.domain.member.entity.Member;
import me.wane.mysql.domain.member.entity.MemberNicknameHistory;
import me.wane.mysql.domain.member.repository.MemberNicknameHistoryRepository;
import me.wane.mysql.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberReadService {

  private final MemberRepository memberRepository;
  private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

  public MemberDto getMember(Long id) {
    Member member = memberRepository.findById(id)
        .orElseThrow();
    return toDto(member);

  }

  public List<MemberNicknameHistoryDto> getNicknameHistoriesByMemberId(Long memberId) {
    return memberNicknameHistoryRepository.findAllByMemberId(memberId)
        .stream()
        .map(this::toDto)
        .toList();
  }

  public MemberDto toDto(Member member) {
    return new MemberDto(member.getId(), member.getEmail(), member.getNickname(),
        member.getBirthday());
  }

  private MemberNicknameHistoryDto toDto(MemberNicknameHistory history) {
    return new MemberNicknameHistoryDto(
        history.getId(),
        history.getMemberId(),
        history.getNickname(),
        history.getCreatedAt()
    );
  }

}
