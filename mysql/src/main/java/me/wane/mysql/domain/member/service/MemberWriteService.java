package me.wane.mysql.domain.member.service;


import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.member.dto.RegisterMemberCommand;
import me.wane.mysql.domain.member.entity.Member;
import me.wane.mysql.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberWriteService {

  private final MemberRepository memberRepository;

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

    return memberRepository.save(member);
  }

}
