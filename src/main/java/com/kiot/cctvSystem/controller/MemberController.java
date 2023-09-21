package com.kiot.cctvSystem.controller;

import com.kiot.cctvSystem.dto.member.MemberDto;
import com.kiot.cctvSystem.service.MemberService;
import com.kiot.cctvSystem.validator.MemberValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    private final MemberValidator memberValidator;

    /** 스프링 파라미터 바인딩 역할 + 검증 기능 **/
    @InitBinder // 여기 해당 컨트롤러에서만 영향, 글로벌 설정은 별도로 해야함.
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(memberValidator); // WebDataBinder에 memberValidator 검증기를 추가하면 여기 컨트롤러에서 검증기를 자동으로 적용 가능
    }

    @GetMapping("/signup")
    public String goSignup(MemberDto memberDto, Model model) {
        model.addAttribute("memberDto", memberDto);
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Validated MemberDto memberDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "/signup";
        }

        try {
            memberService.signup(memberDto);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/signup";
        }

        return "redirect:/login";
    }

}
