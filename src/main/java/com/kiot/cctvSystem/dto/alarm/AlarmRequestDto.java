package com.kiot.cctvSystem.dto.alarm;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlarmRequestDto {
    private Long device_id; // 카메라 아이디
    private Long region; // 구역
    private Long oid; // 객체 번호
    private int count; // 횟수
    private String detected_time; // 측정 시간
    private int level; // 경보레벨(0~2)

    public AlarmRequestDto(Long device_id, Long region, Long oid, int count, String detected_time, int level) {
        this.device_id = device_id;
        this.region = region;
        this.oid = oid;
        this.count = count;
        this.detected_time = detected_time;
        this.level = level;
    }
}
