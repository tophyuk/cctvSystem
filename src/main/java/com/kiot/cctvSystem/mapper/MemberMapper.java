package com.kiot.cctvSystem.mapper;

import com.kiot.cctvSystem.domain.Member;
import com.kiot.cctvSystem.domain.Role;
import com.kiot.cctvSystem.dto.member.MemberDto;
import com.kiot.cctvSystem.dto.member.MemberEditDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;


@Component
public class MemberMapper {

    public MemberDto toMemberDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getMemberId(),
                member.getName(),
                member.getPassword(),
                member.getEmail(),
                member.getTelNum(),
                member.getRole(),
                member.getDeleteYn()
        );
    }

    public Member toMemberEntity(MemberDto memberDto) {
        return Member.builder()
                .memberId(memberDto.getMemberId())
                .name(memberDto.getName())
                .password(memberDto.getPassword())
                .email(memberDto.getEmail())
                .telNum(memberDto.getTelNum())
                .role(Role.USER)
                .deleteYn(memberDto.getDeleteYn())
                .build();
    }

    public Page<MemberDto> toMemberDtoList(Page<Member> boardList){
        Page<MemberDto> memberDtoList = boardList.map(member -> new MemberDto(
                member.getId(),
                member.getMemberId(),
                member.getName(),
                member.getPassword(),
                member.getEmail(),
                member.getTelNum(),
                member.getRole(),
                member.getDeleteYn()
        ));
        return memberDtoList;
    }

    public MemberEditDto toMemberEditDto(Member member) {
        return new MemberEditDto(
                member.getId(),
                member.getMemberId(),
                member.getName(),
                member.getEmail(),
                member.getTelNum(),
                member.getRole()
        );
    }

    public Member toMemberEditEntity(MemberEditDto memberEditDto) {
        return Member.builder()
                .id(memberEditDto.getId())
                .name(memberEditDto.getName())
                .email(memberEditDto.getEmail())
                .telNum(memberEditDto.getTelNum())
                .role(memberEditDto.getRole())
                .build();
    }
}
