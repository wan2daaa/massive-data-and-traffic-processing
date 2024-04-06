package me.wane.mysql.domain.member.service;


import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.member.dto.RegisterMemberCommand;
import me.wane.mysql.domain.member.entity.Member;
import me.wane.mysql.domain.member.entity.MemberNicknameHistory;
import me.wane.mysql.domain.member.repository.MemberNicknameHistoryRepository;
import me.wane.mysql.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@RequiredArgsConstructor
@Service
public class MemberWriteService {

  private final MemberRepository memberRepository;
  private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

  @Transactional //프록시 방식으로 동작, inner 함수를 호출 하면 제대로 동작하지 않는 이슈
  public Member register(RegisterMemberCommand command) {
    /*
     * 목표 - 회원정보(이메일, 닉네임, 생년월일)을 등록한다.
     *     - 닉네임은 10자를 넘길 수 없다.
     * 파라미터 - memberRegisterCommand
     * Member member = Member.of(memberRegisterCommand);
     * memberRepository.save(member);
     */

    Member member = Member.builder()
        .email(command.email())
        .nickname(command.nickname())
        .birthday(command.birthday())
        .build();
//    TransactionTemplate transactionTemplate = new TransactionTemplate();
//    transactionTemplate.execute();
    Member savedMember = memberRepository.save(member);
    saveMemberNicknameHistory(savedMember);
    return savedMember;
  }

  public void changeNickname(Long memberId, String nickname) {
    /**
     * 1. 회원의 이름을 변경
     * 2. 변경 내역을 저장한다.
     */
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    member.changeNickname(nickname);
    memberRepository.save(member);

    saveMemberNicknameHistory(member);
  }

  private void saveMemberNicknameHistory(Member member) {
    MemberNicknameHistory history = MemberNicknameHistory
        .builder()
        .memberId(member.getId())
        .nickname(member.getNickname())
        .build();
    memberNicknameHistoryRepository.save(history);
  }

}
