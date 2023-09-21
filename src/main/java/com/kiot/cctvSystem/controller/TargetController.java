package com.kiot.cctvSystem.controller;

import com.kiot.cctvSystem.dto.alarm.AlarmSearchDto;
import com.kiot.cctvSystem.service.TargetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/target")
public class TargetController {

    private final TargetService targetService;


    @GetMapping("/graphs/{type}")
    @ResponseBody
    public ResponseEntity<?> selectTargetCounts(@PathVariable String type, @ModelAttribute AlarmSearchDto alarmSearchDto) {
        List<Map<String, Object>> result = targetService.getTargetCountList(type, alarmSearchDto);
        return ResponseEntity.ok(result);
    }
}
