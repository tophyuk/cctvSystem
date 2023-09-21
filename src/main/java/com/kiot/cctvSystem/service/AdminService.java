package com.kiot.cctvSystem.service;

import com.kiot.cctvSystem.domain.Member;
import com.kiot.cctvSystem.domain.Role;
import com.kiot.cctvSystem.dto.member.MemberEditDto;
import com.kiot.cctvSystem.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;

    public Page<Member> getMemberList(Character deleteYn, int pageNumber, int pageSize, String searchType, String searchKeyword) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Member> allByDeleteYn = memberRepository.selectAllMembers(deleteYn, searchType, searchKeyword, pageable);
        return allByDeleteYn;
    }

    public Member getMember(Long id) {
        Member member = memberRepository.findByIdAndDeleteYn(id, 'N').orElseThrow(() -> new IllegalArgumentException("해당 회원은 존재하지 않습니다."));
        return member;
    }

    public void editMember(Member member) {
        Member findMember = memberRepository.findByIdAndDeleteYn(member.getId(), 'N').orElseThrow(() -> new IllegalArgumentException("해당 회원은 존재하지 않습니다."));

        findMember.updateMember(member.getName(), member.getEmail(), member.getTelNum(), member.getRole());
        memberRepository.save(findMember);
    }

    public void deleteMember(Long id) {
        Member findMember = memberRepository.findByIdAndDeleteYn(id, 'N').orElseThrow(() -> new IllegalArgumentException("해당 회원은 존재하지 않습니다."));
        findMember.deleteMember('Y');
        memberRepository.save(findMember);
    }
}
