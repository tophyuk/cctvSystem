package com.kiot.cctvSystem.mapper;

import com.kiot.cctvSystem.domain.Alarm;
import com.kiot.cctvSystem.domain.Device;
import com.kiot.cctvSystem.domain.Target;
import com.kiot.cctvSystem.dto.alarm.AlarmRequestDto;
import com.kiot.cctvSystem.dto.alarm.AlarmStatDataDto;
import com.kiot.cctvSystem.repository.DeviceRepository;
import com.kiot.cctvSystem.repository.TargetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class AlarmMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final TargetRepository targetRepository;
    private final DeviceRepository deviceRepository;

    public AlarmRequestDto toAlarmRequestDto(Alarm alarm) {

        return new AlarmRequestDto(
                alarm.getDevice().getId(),
                alarm.getRegion(),
                alarm.getTarget().getId(),
                alarm.getCount(),
                alarm.getDetectedTime().format(FORMATTER),
                alarm.getLevel()
        );
    }

    public AlarmStatDataDto toAlarmStatDataDto(Alarm alarm) {
        return new AlarmStatDataDto(
                alarm.getDevice().getName(),
                alarm.getTarget().getName(),
                alarm.getLevel(),
                alarm.getDetectedTime().format(FORMATTER)
        );
    }


    public Alarm toAlarm(AlarmRequestDto alarmRequestDto) {

        Long deviceId = alarmRequestDto.getDevice_id();
        Long oid = alarmRequestDto.getOid();

        Device device = deviceRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장비는 존재하지 않습니다."));

        Target target = targetRepository.findById(oid)
                .orElseThrow(() -> new IllegalArgumentException("해당 대상은 존재하지 않습니다."));


        return Alarm.builder()
                .device(device)
                .region(alarmRequestDto.getRegion())
                .target(target)
                .count(alarmRequestDto.getCount())
                .detectedTime(LocalDateTime.parse(alarmRequestDto.getDetected_time(), FORMATTER))
                .level(alarmRequestDto.getLevel())
                .build();
    }


}
