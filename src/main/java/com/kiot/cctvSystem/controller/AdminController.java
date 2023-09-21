package com.kiot.cctvSystem.controller;

import com.kiot.cctvSystem.domain.Member;
import com.kiot.cctvSystem.domain.Role;
import com.kiot.cctvSystem.dto.SearchDto;
import com.kiot.cctvSystem.dto.member.MemberDto;
import com.kiot.cctvSystem.dto.member.MemberEditDto;
import com.kiot.cctvSystem.mapper.MemberMapper;
import com.kiot.cctvSystem.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final MemberMapper memberMapper;

    @GetMapping("/devices")
    public String adminDevicesPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        MemberDto memberDto = (MemberDto) principal;

        model.addAttribute("memberDto", memberDto);

        return "/admin/device";
    }


    @GetMapping("/members")
    public String adminMembersPage(SearchDto searchDto, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        MemberDto memberDto = (MemberDto) principal;

        model.addAttribute("memberDto", memberDto);
        model.addAttribute("searchDto", searchDto);

        return "/admin/member";
    }

    @GetMapping("/members/list")
    @ResponseBody
    public ResponseEntity<?> adminMembers(SearchDto searchDto) {
        int pageNum = searchDto.getPageNum();
        int pageSize = searchDto.getPageSize();
        String searchType = searchDto.getSearchType();
        String searchKeyword = searchDto.getSearchKeyword();

        if (searchType != null && searchKeyword != null) {
            if (searchType.equals("") || searchKeyword.equals("")) {
                searchType = null;
                searchKeyword = null;
            }
        }

        Page<Member> memberList = adminService.getMemberList('N', pageNum - 1, pageSize, searchType, searchKeyword);
        Page<MemberDto> memberDtoList = memberMapper.toMemberDtoList(memberList);

        return ResponseEntity.ok(memberDtoList);
    }

    @GetMapping("/members/{id}")
    public String adminMemberDetail(@PathVariable Long id,
                                    SearchDto searchDto,
                                    Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        MemberDto memberDto = (MemberDto) principal;

        Member member = adminService.getMember(id);
        MemberDto memberByIdDto = memberMapper.toMemberDto(member);

        model.addAttribute("searchDto", searchDto);
        model.addAttribute("memberDto", memberDto);
        model.addAttribute("memberByIdDto", memberByIdDto);
        model.addAttribute("id", id);

        return "/admin/memberDetail";
    }

    @GetMapping("/members/{id}/edit")
    public String adminMemberEdit(@PathVariable Long id,
                                  SearchDto searchDto,
                                  Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        MemberDto memberDto = (MemberDto) principal;

        Member member = adminService.getMember(id);
        MemberEditDto memberEditDto = memberMapper.toMemberEditDto(member);

        model.addAttribute("roles", Role.values());
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("memberDto", memberDto);
        model.addAttribute("memberEditDto", memberEditDto);
        model.addAttribute("id", id);

        return "/admin/memberEdit";

    }

    @PutMapping("/members/{id}/edit")
    public String adminMemberEdit(@PathVariable Long id,
                                  MemberEditDto memberEditDto,
                                  RedirectAttributes redirectAttributes) {

        Member member = memberMapper.toMemberEditEntity(memberEditDto);
        adminService.editMember(member);

/*
        redirectAttributes.addAttribute("pageNum", searchDto.getPageNum());
        redirectAttributes.addAttribute("searchType", searchDto.getSearchType());
        redirectAttributes.addAttribute("keyword", searchDto.getSearchKeyword());
*/

        return "redirect:/admin/members/{id}";

    }

    @DeleteMapping("/members/{id}/delete")
    public String adminMemberDelete(@PathVariable Long id,
                                    RedirectAttributes redirectAttributes) {

        adminService.deleteMember(id);
        return "redirect:/admin/members";
    }
}
