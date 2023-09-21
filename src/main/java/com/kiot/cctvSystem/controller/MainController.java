package com.kiot.cctvSystem.controller;

import com.kiot.cctvSystem.dto.member.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String root() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().toString().indexOf("ROLE_ANONYMOUS") > -1) {
            // 익명
            return "login";
        } else {
            // 로그인한 사용자
            return "redirect:/main";
        }

    }

    @GetMapping("/main")
    public String main(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        MemberDto memberDto = (MemberDto) principal;

        model.addAttribute("memberDto", memberDto);

        return "index";
    }

}
