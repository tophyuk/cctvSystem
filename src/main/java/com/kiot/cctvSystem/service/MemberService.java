package com.kiot.cctvSystem.service;

import com.kiot.cctvSystem.domain.Member;
import com.kiot.cctvSystem.dto.member.MemberDto;
import com.kiot.cctvSystem.mapper.MemberMapper;
import com.kiot.cctvSystem.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signup(MemberDto memberDto) {
        memberDto.setDeleteYn('N');
        Member member = memberMapper.toMemberEntity(memberDto);
        //패스워드 암호화 후 적용
        member.updatePassword(bCryptPasswordEncoder.encode(member.getPassword()));
        log.info("password = {}", memberDto.getPassword());

        memberRepository.save(member);
    }


    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        // 일반 로그인일 때는 무조건 loginType이 basic으로 고정이기 떄문에 찾을때도 basic으로 찾기
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new UsernameNotFoundException(memberId + "는 존재하지 않는 이메일입니다."));
        MemberDto memberDto = memberMapper.toMemberDto(member);

        return memberDto;
    }

    public boolean checkMemberId(String memberId) {
        boolean existsByUsername = memberRepository.existsByMemberId(memberId);
        return existsByUsername;
    }
}
