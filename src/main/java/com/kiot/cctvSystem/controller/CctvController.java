package com.kiot.cctvSystem.controller;

import com.kiot.cctvSystem.domain.Device;
import com.kiot.cctvSystem.dto.*;
import com.kiot.cctvSystem.dto.member.MemberDto;
import com.kiot.cctvSystem.mapper.DeviceMapper;
import com.kiot.cctvSystem.service.CctvService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CctvController {

    private final CctvService cctvService;
    private final DeviceMapper deviceMapper;
    @GetMapping("/cctv/main")
    public String cctvMain(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        MemberDto memberDto = (MemberDto) principal;

        List<Device> allDeviceList = cctvService.getAllDeviceList();

        List<DeviceDto> deviceDtoList = allDeviceList.stream()
                .map(device -> deviceMapper.toDeviceDto(device))
                .collect(Collectors.toList());

        model.addAttribute("memberDto", memberDto);
        model.addAttribute("deviceDtoList", deviceDtoList);


        return "/cctv/cctvMonitoring";
    }

}
