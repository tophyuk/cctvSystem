package com.kiot.cctvSystem.controller;

import com.kiot.cctvSystem.domain.Alarm;
import com.kiot.cctvSystem.dto.alarm.AlarmRequestDto;
import com.kiot.cctvSystem.dto.alarm.AlarmSearchDto;
import com.kiot.cctvSystem.dto.alarm.AlarmStatDataDto;
import com.kiot.cctvSystem.dto.member.MemberDto;
import com.kiot.cctvSystem.handler.CustomWebSocketHandler;
import com.kiot.cctvSystem.mapper.AlarmMapper;
import com.kiot.cctvSystem.service.AlarmService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmService alarmService;
    private final AlarmMapper alarmMapper;
    private final CustomWebSocketHandler customWebSocketHandler;


    @GetMapping("/stats/year")
    public String alarmStatsYear(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        MemberDto memberDto = (MemberDto) principal;

        model.addAttribute("memberDto", memberDto);

        return "/alarm/alarmYear";
    }

    @GetMapping("/stats/month")
    public String alarmStatsMonth(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        MemberDto memberDto = (MemberDto) principal;

        model.addAttribute("memberDto", memberDto);

        return "/alarm/alarmMonth";
    }

    @GetMapping("/stats/day")
    public String alarmStatsDay(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        MemberDto memberDto = (MemberDto) principal;

        model.addAttribute("memberDto", memberDto);

        return "/alarm/alarmDay";
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<?> insertAlarm(@RequestBody AlarmRequestDto alarmRequestDto)  {

        // jenkins 연동 테스트를 위한 주석
        log.info("alarmDto class={}", alarmRequestDto);
        Alarm alarm = alarmMapper.toAlarm(alarmRequestDto);
        alarmService.createAlarm(alarm);

        Long deviceId = alarmRequestDto.getDevice_id();
        String url = alarmService.getURL(deviceId);

        customWebSocketHandler.sendMessageToAll(url);

        return ResponseEntity.ok(alarmRequestDto);

    }

    @GetMapping("/graphs/{type}")
    @ResponseBody
    public ResponseEntity<?> selectAlarmCounts(@PathVariable String type, @ModelAttribute AlarmSearchDto alarmSearchDto) {
        List<Map<String, Integer>> result = alarmService.getAlarmCounts(type, alarmSearchDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/graphs/excel/{type}")
    public void graphExcelDownload(@PathVariable String type, @ModelAttribute AlarmSearchDto alarmSearchDto, HttpServletResponse response) throws IOException {
        List<Map<String, Integer>> result = alarmService.getAlarmCounts(type, alarmSearchDto);
        alarmService.createGraphExcel(result, type, response);
    }

    @GetMapping("/tables/{type}")
    @ResponseBody
    public ResponseEntity<?> selectAlarmList(@PathVariable String type, @ModelAttribute AlarmSearchDto alarmSearchDto) {
        List<Alarm> alarmList = alarmService.getAlarmList(alarmSearchDto);
        List<AlarmStatDataDto> alarmDtoList = alarmList.stream()
                .map(alarm -> alarmMapper.toAlarmStatDataDto(alarm))
                .collect(Collectors.toList());

        return ResponseEntity.ok(alarmDtoList);
    }

    @GetMapping("/tables/excel/{type}")
    public void dataExcelDownload(@PathVariable String type, @ModelAttribute AlarmSearchDto alarmSearchDto, HttpServletResponse response) throws IOException {
        List<Alarm> alarmList = alarmService.getAlarmList(alarmSearchDto);
        List<AlarmStatDataDto> alarmDtoList = alarmList.stream()
                .map(alarm -> alarmMapper.toAlarmStatDataDto(alarm))
                .collect(Collectors.toList());

        alarmService.createDataExcel(alarmDtoList, response);
    }

}
