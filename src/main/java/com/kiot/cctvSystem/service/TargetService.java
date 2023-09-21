package com.kiot.cctvSystem.service;

import com.kiot.cctvSystem.dto.alarm.AlarmSearchDto;
import com.kiot.cctvSystem.repository.TargetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TargetService {

    private final TargetRepository targetRepository;

    public List<Map<String, Object>> getTargetCountList(String type, AlarmSearchDto alarmSearchDto){
        int year = alarmSearchDto.getYear();
        Integer month = alarmSearchDto.getMonth();
        Integer day = alarmSearchDto.getDay();

        // 결과 데이터를 담을 리스트 생성
        List<Map<String, Object>> result = new ArrayList<>();
        result = targetRepository.getTargetCounts(year, month, day);

        return result;

    }
}
