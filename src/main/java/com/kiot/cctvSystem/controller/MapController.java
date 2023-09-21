package com.kiot.cctvSystem.controller;

import com.kiot.cctvSystem.domain.Alarm;
import com.kiot.cctvSystem.domain.Device;
import com.kiot.cctvSystem.dto.DeviceDto;
import com.kiot.cctvSystem.dto.alarm.AlarmStatDataDto;
import com.kiot.cctvSystem.dto.member.MemberDto;
import com.kiot.cctvSystem.mapper.AlarmMapper;
import com.kiot.cctvSystem.mapper.DeviceMapper;
import com.kiot.cctvSystem.service.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/map")
@Slf4j
public class MapController {

    private final MapService mapService;
    private final AlarmMapper alarmMapper;
    private final DeviceMapper deviceMapper;

    @GetMapping("/main")
    public String mapMain(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        MemberDto memberDto = (MemberDto) principal;

        model.addAttribute("memberDto", memberDto);

        return "/map/map";
    }

    @GetMapping("/devices")
    @ResponseBody
    public ResponseEntity<?> selectMapDeviceList() {
        List<Device> deviceList = mapService.getDeviceList();
        List<DeviceDto> deviceDtoList = deviceList.stream().map(device -> deviceMapper.toDeviceDto(device))
                .collect(Collectors.toList());
        return ResponseEntity.ok(deviceDtoList);
    }
    @GetMapping("/alarms")
    @ResponseBody
    public ResponseEntity<?> selectMapAlarmList(@RequestParam Long deviceId) {
        List<Alarm> mapAlarmList = mapService.getMapAlarmList(deviceId);
        List<AlarmStatDataDto> alarmStatDataDtoList = mapAlarmList.stream().map(alarm -> alarmMapper.toAlarmStatDataDto(alarm))
                .collect(Collectors.toList());
        return ResponseEntity.ok(alarmStatDataDtoList);
    }

}
