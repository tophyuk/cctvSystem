package com.kiot.cctvSystem.validator;

import com.kiot.cctvSystem.domain.Member;
import com.kiot.cctvSystem.dto.member.MemberDto;
import com.kiot.cctvSystem.mapper.MemberMapper;
import com.kiot.cctvSystem.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor

public class MemberValidator implements Validator {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberDto memberDto = (MemberDto) target;
        Member member = memberMapper.toMemberEntity(memberDto);

        // 비밀번호, 비밀번호 확인이 같은지 확인
        if (!memberDto.getPassword().equals(memberDto.getPassword2())) {
            errors.rejectValue("password", "passwordUnmatched",
                    "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 사용자명 중복 체크
        if (memberService.checkMemberId(member.getMemberId())) {
            errors.rejectValue("memberId", "memberIdDuplication", "이미 사용중인 아이디 입니다.");
        }

    }
}